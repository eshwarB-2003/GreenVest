/*package com.greenvest.controller;


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

 */


package com.greenvest.controller;

import com.greenvest.model.CreditListing;
import com.greenvest.model.Transaction;
import com.greenvest.service.MarketplaceService;
import com.greenvest.service.ReceiptService;

import java.util.List;

public class MarketplaceController {

    private final MarketplaceService marketplaceService;
    private final ReceiptService receiptService;

    public MarketplaceController(MarketplaceService marketplaceService,
                                 ReceiptService receiptService) {
        this.marketplaceService = marketplaceService;
        this.receiptService = receiptService;
    }

    // Called by BuyerMenu to show the marketplace
    public List<CreditListing> getAvailableListings() {
        return marketplaceService.getAvailableListings();
    }

    // Called when buyer chooses to purchase
    public Transaction purchase(String buyerId, String listingId, int quantity) {
        return marketplaceService.purchase(buyerId, listingId, quantity);
    }

    // Used to print a formatted receipt in the console
    public String getReceiptText(Transaction transaction) {
        if (transaction == null) {
            return "No transaction provided.";
        }
        return receiptService.generateReceipt(transaction).getRawText();
    }

    // Optional helper: by transaction id (if you ever use it)
    public String getReceiptTextById(String transactionId) {
        return receiptService.getFormattedReceiptText(transactionId);
    }
}
