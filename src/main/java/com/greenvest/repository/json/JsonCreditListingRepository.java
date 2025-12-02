package com.greenvest.repository.json;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.model.CreditListing;
import com.greenvest.model.ListingStatus;
import com.greenvest.repository.CreditListingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonCreditListingRepository
        extends AbstractJsonRepository<CreditListing>
        implements CreditListingRepository {

    public JsonCreditListingRepository(JsonFileUtil fileUtil) {
        super(fileUtil, "listings.json", CreditListing[].class);
    }

    @Override
    public Optional<CreditListing> findById(String id) {
        return readAllInternal().stream()
                .filter(l -> id.equals(l.getId()))
                .findFirst();
    }

    @Override
    public List<CreditListing> findActiveListings() {
        return readAllInternal().stream()
                .filter(l -> l.getStatus() == ListingStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditListing> findBySellerId(String sellerId) {
        return readAllInternal().stream()
                .filter(l -> sellerId.equals(l.getSellerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditListing> findAll() {
        return new ArrayList<>(readAllInternal());
    }

    @Override
    public CreditListing save(CreditListing listing) {
        List<CreditListing> all = readAllInternal();
        all.removeIf(l -> l.getId().equals(listing.getId()));
        all.add(listing);
        writeAllInternal(all);
        return listing;
    }
}

