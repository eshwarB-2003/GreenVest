package com.greenvest.repository;

import com.greenvest.model.CreditListing;

import java.util.List;
import java.util.Optional;

public interface CreditListingRepository {

    Optional<CreditListing> findById(String id);

    List<CreditListing> findActiveListings();

    List<CreditListing> findBySellerId(String sellerId);

    List<CreditListing> findAll();

    CreditListing save(CreditListing listing);
}
