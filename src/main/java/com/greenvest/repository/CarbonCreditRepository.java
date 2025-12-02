package com.greenvest.repository;

import com.greenvest.model.CarbonCredit;

import java.util.List;
import java.util.Optional;

public interface CarbonCreditRepository {

    Optional<CarbonCredit> findById(String id);

    List<CarbonCredit> findBySellerId(String sellerId);

    List<CarbonCredit> findAll();

    CarbonCredit save(CarbonCredit credit);
}
