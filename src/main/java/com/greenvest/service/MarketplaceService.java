/*package com.greenvest.service;

import com.greenvest.common.Preconditions;
import com.greenvest.model.CreditListing;
import com.greenvest.model.ListingStatus;
import com.greenvest.model.Transaction;
import com.greenvest.repository.CreditListingRepository;
import com.greenvest.repository.TransactionRepository;

import java.util.List;

public class MarketplaceService {

    private final CreditListingRepository listingRepository;
    private final TransactionRepository transactionRepository;

    public MarketplaceService(CreditListingRepository listingRepository,
                              TransactionRepository transactionRepository) {
        this.listingRepository = listingRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<CreditListing> getActiveListings() {
        return listingRepository.findActiveListings();
    }

    public Transaction purchase(String buyerId, String listingId, int quantity) {
        Preconditions.requireNonBlank(buyerId, "buyerId is required");
        Preconditions.requireNonBlank(listingId, "listingId is required");
        Preconditions.require(quantity > 0, "Quantity must be positive");

        CreditListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if (listing.getStatus() != ListingStatus.ACTIVE) {
            throw new IllegalStateException("Listing is not active");
        }

        if (quantity > listing.getQuantity()) {
            throw new IllegalArgumentException("Not enough quantity in listing");
        }

        double totalAmount = quantity * listing.getPricePerCredit();

        Transaction tx = new Transaction(
                buyerId,
                listing.getSellerId(),
                listing.getId(),
                quantity,
                totalAmount
        );
        transactionRepository.save(tx);

        int remaining = listing.getQuantity() - quantity;
        if (remaining == 0) {
            listing.setStatus(ListingStatus.CLOSED);
        }
        listingRepository.save(listing);

        return tx;
    }
}
*/

package com.greenvest.service;

import com.greenvest.common.Preconditions;
import com.greenvest.model.CreditListing;
import com.greenvest.model.ListingStatus;
import com.greenvest.model.Transaction;
import com.greenvest.repository.CreditListingRepository;
import com.greenvest.repository.TransactionRepository;

import java.util.List;

public class MarketplaceService {

    private final CreditListingRepository listingRepository;
    private final TransactionRepository transactionRepository;

    public MarketplaceService(CreditListingRepository listingRepository,
                              TransactionRepository transactionRepository) {
        this.listingRepository = listingRepository;
        this.transactionRepository = transactionRepository;
    }

    // Existing method – used internally
    public List<CreditListing> getActiveListings() {
        return listingRepository.findActiveListings();
    }

    // New alias – this is what MarketplaceController expects
    public List<CreditListing> getAvailableListings() {
        return getActiveListings();
    }

    public Transaction purchase(String buyerId, String listingId, int quantity) {
        Preconditions.requireNonBlank(buyerId, "buyerId is required");
        Preconditions.requireNonBlank(listingId, "listingId is required");
        Preconditions.require(quantity > 0, "Quantity must be positive");

        CreditListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if (listing.getStatus() != ListingStatus.ACTIVE) {
            throw new IllegalStateException("Listing is not active");
        }
        if (quantity > listing.getQuantity()) {
            throw new IllegalArgumentException("Not enough quantity in listing");
        }

        double totalAmount = quantity * listing.getPricePerCredit();

        Transaction tx = new Transaction(
                buyerId,
                listing.getSellerId(),
                listing.getId(),
                quantity,
                totalAmount
        );
        transactionRepository.save(tx);

        int remaining = listing.getQuantity() - quantity;
        if (remaining == 0) {
            listing.setStatus(ListingStatus.CLOSED);
        }
        // If your CreditListing has setQuantity(), you can also update it here:
        // listing.setQuantity(remaining);

        listingRepository.save(listing);
        return tx;
    }
}
