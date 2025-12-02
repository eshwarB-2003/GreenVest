package com.greenvest.controller;

import com.greenvest.model.AuditLogEntry;
import com.greenvest.model.CarbonCredit;
import com.greenvest.model.CreditListing;
import com.greenvest.model.SustainabilityAction;
import com.greenvest.service.AdminService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public List<SustainabilityAction> getPendingActions() {
        return adminService.getPendingActions();
    }

    public void approveAction(String adminId, String actionId, String note, int quantity, LocalDate expiryDate) {
        Instant expiryInstant = expiryDate != null
                ? expiryDate.atStartOfDay(ZoneOffset.UTC).toInstant()
                : null;
        adminService.approveAction(adminId, actionId, note, quantity, expiryInstant);
    }

    public void rejectAction(String adminId, String actionId, String note) {
        adminService.rejectAction(adminId, actionId, note);
    }

    public List<CreditListing> getPendingListings() {
        return adminService.getPendingListings();
    }

    public void approveListing(String adminId, String listingId) {
        adminService.approveListing(adminId, listingId);
    }

    public void rejectListing(String adminId, String listingId, String reason) {
        adminService.rejectListing(adminId, listingId, reason);
    }

    public double getBaseMinimumPrice() {
        return adminService.getBaseMinimumPrice();
    }

    public void setBaseMinimumPrice(String adminId, double price) {
        adminService.setBaseMinimumPrice(adminId, price);
    }

    public List<CarbonCredit> runExpiryCheck(String adminId) {
        return adminService.runExpiryCheck(adminId);
    }

    public List<AuditLogEntry> getAuditLog() {
        return adminService.getAuditLog();
    }
}
