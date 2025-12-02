package com.greenvest.repository.json;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.model.CarbonCredit;
import com.greenvest.repository.CarbonCreditRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JsonCarbonCreditRepository
        extends AbstractJsonRepository<CarbonCredit>
        implements CarbonCreditRepository {

    public JsonCarbonCreditRepository(JsonFileUtil fileUtil) {
        super(fileUtil, "carbon-credits.json", CarbonCredit[].class);
    }

    @Override
    public Optional<CarbonCredit> findById(String id) {
        return readAllInternal().stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst();
    }

    @Override
    public List<CarbonCredit> findBySellerId(String sellerId) {
        return readAllInternal().stream()
                .filter(c -> sellerId.equals(c.getSellerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarbonCredit> findAll() {
        return new ArrayList<>(readAllInternal());
    }

    @Override
    public CarbonCredit save(CarbonCredit credit) {
        List<CarbonCredit> all = readAllInternal();
        all.removeIf(c -> c.getId().equals(credit.getId()));
        all.add(credit);
        writeAllInternal(all);
        return credit;
    }
}
