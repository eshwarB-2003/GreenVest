package com.greenvest.repository;

import com.greenvest.model.SustainabilityAction;

import java.util.List;
import java.util.Optional;

public interface SustainabilityActionRepository {

    Optional<SustainabilityAction> findById(String id);

    List<SustainabilityAction> findBySellerId(String sellerId);

    List<SustainabilityAction> findAll();

    SustainabilityAction save(SustainabilityAction action);
}
