package com.greenvest.controller;

import com.greenvest.model.CarbonCredit;
import com.greenvest.model.CreditListing;
import com.greenvest.model.SustainabilityAction;
import com.greenvest.service.SellerService;

import java.util.List;

public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    public SustainabilityAction submitAction(String sellerId, String description, String proofPath) {
        return sellerService.submitSustainabilityAction(sellerId, description, proofPath);
    }

    public List<SustainabilityAction> getActionsForSeller(String sellerId) {
        return sellerService.getActionsForSeller(sellerId);
    }

    public List<CarbonCredit> getVerifiedCredits(String sellerId) {
        return sellerService.getVerifiedCreditsForSeller(sellerId);
    }

    public CreditListing listCredits(String sellerId, String creditId, int quantity, double pricePerCredit) {
        return sellerService.listCreditsForSale(sellerId, creditId, quantity, pricePerCredit);
    }

    public List<CreditListing> getListingsForSeller(String sellerId) {
        return sellerService.getListingsForSeller(sellerId);
    }
}
