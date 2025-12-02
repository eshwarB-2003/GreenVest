package com.greenvest.repository.json;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.model.SustainabilityAction;
import com.greenvest.repository.SustainabilityActionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonSustainabilityActionRepository
        extends AbstractJsonRepository<SustainabilityAction>
        implements SustainabilityActionRepository {

    public JsonSustainabilityActionRepository(JsonFileUtil fileUtil) {
        super(fileUtil, "sustainability-actions.json", SustainabilityAction[].class);
    }

    @Override
    public Optional<SustainabilityAction> findById(String id) {
        return readAllInternal().stream()
                .filter(a -> id.equals(a.getId()))
                .findFirst();
    }

    @Override
    public List<SustainabilityAction> findBySellerId(String sellerId) {
        return readAllInternal().stream()
                .filter(a -> sellerId.equals(a.getSellerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SustainabilityAction> findAll() {
        return new ArrayList<>(readAllInternal());
    }

    @Override
    public SustainabilityAction save(SustainabilityAction action) {
        List<SustainabilityAction> all = readAllInternal();
        all.removeIf(a -> a.getId().equals(action.getId()));
        all.add(action);
        writeAllInternal(all);
        return action;
    }
}
