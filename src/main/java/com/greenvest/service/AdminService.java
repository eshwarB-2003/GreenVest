package com.greenvest.service;

import com.greenvest.common.Preconditions;
import com.greenvest.common.SystemConfig;
import com.greenvest.model.*;
import com.greenvest.repository.AuditLogRepository;
import com.greenvest.repository.CarbonCreditRepository;
import com.greenvest.repository.CreditListingRepository;
import com.greenvest.repository.SustainabilityActionRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class AdminService {

    private final SustainabilityActionRepository actionRepository;
    private final CarbonCreditRepository creditRepository;
    private final CreditListingRepository listingRepository;
    private final AuditLogRepository auditLogRepository;
    private final SystemConfig systemConfig;

    public AdminService(SustainabilityActionRepository actionRepository,
                        CarbonCreditRepository creditRepository,
                        CreditListingRepository listingRepository,
                        AuditLogRepository auditLogRepository,
                        SystemConfig systemConfig) {

        this.actionRepository = Preconditions.requireNonNull(actionRepository, "actionRepository is required");
        this.creditRepository = Preconditions.requireNonNull(creditRepository, "creditRepository is required");
        this.listingRepository = Preconditions.requireNonNull(listingRepository, "listingRepository is required");
        this.auditLogRepository = Preconditions.requireNonNull(auditLogRepository, "auditLogRepository is required");
        this.systemConfig = Preconditions.requireNonNull(systemConfig, "systemConfig is required");
    }

    // ---------- Sustainability actions ----------

    public List<SustainabilityAction> getPendingActions() {
        return actionRepository.findAll().stream()
                .filter(a -> a.getStatus() == SustainabilityActionStatus.SUBMITTED
                        || a.getStatus() == SustainabilityActionStatus.UNDER_REVIEW)
                .collect(Collectors.toList());
    }

    public void approveAction(String adminId, String actionId, String note, int quantity, Instant expiryDate) {
        Preconditions.requireNonBlank(adminId, "adminId is required");
        Preconditions.requireNonBlank(actionId, "actionId is required");
        Preconditions.require(quantity > 0, "Quantity must be positive");

        SustainabilityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        action.setStatus(SustainabilityActionStatus.APPROVED);
        action.setAdminReviewNote(note);
        action.setReviewedAt(Instant.now());
        actionRepository.save(action);

        CarbonCredit credit = new CarbonCredit(
                action.getSellerId(),
                action.getId(),
                quantity,
                expiryDate
        );
        credit.setLifecycleState(CreditLifecycleState.VERIFIED);
        creditRepository.save(credit);

        log(adminId, "APPROVE_ACTION",
                "Approved sustainability action " + actionId + " and issued " + quantity + " credits.");
    }

    public void rejectAction(String adminId, String actionId, String note) {
        Preconditions.requireNonBlank(adminId, "adminId is required");
        Preconditions.requireNonBlank(actionId, "actionId is required");

        SustainabilityAction action = actionRepository.findById(actionId)
                .orElseThrow(() -> new IllegalArgumentException("Action not found"));

        action.setStatus(SustainabilityActionStatus.REJECTED);
        action.setAdminReviewNote(note);
        action.setReviewedAt(Instant.now());
        actionRepository.save(action);

        log(adminId, "REJECT_ACTION",
                "Rejected sustainability action " + actionId + " with note: " + note);
    }

    // ---------- Listings ----------

    public List<CreditListing> getPendingListings() {
        return listingRepository.findAll().stream()
                .filter(l -> l.getStatus() == ListingStatus.PENDING_APPROVAL)
                .collect(Collectors.toList());
    }

    public void approveListing(String adminId, String listingId) {
        Preconditions.requireNonBlank(adminId, "adminId is required");
        Preconditions.requireNonBlank(listingId, "listingId is required");

        CreditListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        listing.setStatus(ListingStatus.ACTIVE);
        listing.setApprovedAt(Instant.now());
        listing.setApprovedByAdminId(adminId);
        listingRepository.save(listing);

        log(adminId, "APPROVE_LISTING",
                "Approved listing " + listingId);
    }

    public void rejectListing(String adminId, String listingId, String reason) {
        Preconditions.requireNonBlank(adminId, "adminId is required");
        Preconditions.requireNonBlank(listingId, "listingId is required");

        CreditListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        listing.setStatus(ListingStatus.SUSPENDED);
        listing.setApprovedAt(Instant.now());
        listing.setApprovedByAdminId(adminId);
        listingRepository.save(listing);

        log(adminId, "REJECT_LISTING",
                "Rejected listing " + listingId + " with reason: " + reason);
    }

    // ---------- Base minimum price ----------

    public double getBaseMinimumPrice() {
        return systemConfig.getBaseMinimumPrice();
    }

    public void setBaseMinimumPrice(String adminId, double price) {
        Preconditions.require(price >= 0.0, "Base price cannot be negative");
        systemConfig.setBaseMinimumPrice(price);
        log(adminId, "SET_BASE_MIN_PRICE",
                "Set base minimum price to " + price);
    }

    // ---------- Expiry check ----------

    public List<CarbonCredit> runExpiryCheck(String adminId) {
        Instant now = Instant.now();
        List<CarbonCredit> all = creditRepository.findAll();

        List<CarbonCredit> expired = all.stream()
                .filter(c -> c.getExpiryDate() != null && c.getExpiryDate().isBefore(now))
                .collect(Collectors.toList());

        for (CarbonCredit credit : expired) {
            credit.setLifecycleState(CreditLifecycleState.EXPIRED);
            creditRepository.save(credit);
        }

        if (!expired.isEmpty()) {
            log(adminId, "RUN_EXPIRY_CHECK",
                    "Expired " + expired.size() + " credits.");
        }

        return expired;
    }

    // ---------- Audit ----------

    public List<AuditLogEntry> getAuditLog() {
        return auditLogRepository.findAll();
    }

    private void log(String adminId, String actionType, String description) {
        AuditLogEntry entry = new AuditLogEntry(adminId, actionType, description);
        auditLogRepository.save(entry);
    }
}
