package com.greenvest.controller;

import com.greenvest.model.CreditListing;
import com.greenvest.model.Transaction;
import com.greenvest.service.MarketplaceService;

import java.util.List;

public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    public List<CreditListing> getMarketplaceListings() {
        return marketplaceService.getActiveListings();
    }

    public Transaction purchase(String buyerId, String listingId, int quantity) {
        return marketplaceService.purchase(buyerId, listingId, quantity);
    }
}
