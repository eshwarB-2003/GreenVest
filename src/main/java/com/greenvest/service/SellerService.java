package com.greenvest.service;

import com.greenvest.common.Preconditions;
import com.greenvest.common.SystemConfig;
import com.greenvest.model.*;
import com.greenvest.repository.CarbonCreditRepository;
import com.greenvest.repository.CreditListingRepository;
import com.greenvest.repository.SustainabilityActionRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SellerService {

    private final SustainabilityActionRepository actionRepository;
    private final CarbonCreditRepository creditRepository;
    private final CreditListingRepository listingRepository;
    private final SystemConfig systemConfig;

    public SellerService(SustainabilityActionRepository actionRepository,
                         CarbonCreditRepository creditRepository,
                         CreditListingRepository listingRepository,
                         SystemConfig systemConfig) {
        this.actionRepository = actionRepository;
        this.creditRepository = creditRepository;
        this.listingRepository = listingRepository;
        this.systemConfig = systemConfig;
    }

    public SustainabilityAction submitSustainabilityAction(String sellerId, String description, String proofPath) {
        Preconditions.requireNonBlank(sellerId, "sellerId is required");
        Preconditions.requireNonBlank(description, "description is required");

        SustainabilityAction action = new SustainabilityAction(sellerId, description, proofPath);
        action.setStatus(SustainabilityActionStatus.SUBMITTED);
        return actionRepository.save(action);
    }

    public List<SustainabilityAction> getActionsForSeller(String sellerId) {
        return actionRepository.findBySellerId(sellerId);
    }

    public List<CarbonCredit> getVerifiedCreditsForSeller(String sellerId) {
        return creditRepository.findBySellerId(sellerId).stream()
                .filter(c -> c.getLifecycleState() == CreditLifecycleState.VERIFIED
                        || c.getLifecycleState() == CreditLifecycleState.LISTED)
                .collect(Collectors.toList());
    }

    public CreditListing listCreditsForSale(String sellerId, String creditId, int quantity, double pricePerCredit) {
        Preconditions.requireNonBlank(sellerId, "sellerId is required");
        Preconditions.requireNonBlank(creditId, "creditId is required");
        Preconditions.require(quantity > 0, "Quantity must be positive");

        double basePrice = systemConfig.getBaseMinimumPrice();
        Preconditions.require(pricePerCredit >= basePrice,
                "Price per credit must be at least base minimum price: " + basePrice);

        CarbonCredit credit = creditRepository.findById(creditId)
                .orElseThrow(() -> new IllegalArgumentException("Credit not found"));

        if (!sellerId.equals(credit.getSellerId())) {
            throw new IllegalArgumentException("Seller does not own this credit");
        }

        Preconditions.require(quantity <= credit.getQuantity(),
                "Not enough credit quantity available");

        credit.setLifecycleState(CreditLifecycleState.LISTED);
        creditRepository.save(credit);

        CreditListing listing = new CreditListing(sellerId, creditId, quantity, pricePerCredit);
        listing.setStatus(ListingStatus.PENDING_APPROVAL);
        return listingRepository.save(listing);
    }

    public List<CreditListing> getListingsForSeller(String sellerId) {
        return listingRepository.findBySellerId(sellerId);
    }
}
