package com.greenvest.repository.json;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.model.AuditLogEntry;
import com.greenvest.repository.AuditLogRepository;

import java.util.ArrayList;
import java.util.List;

public class JsonAuditLogRepository
        extends AbstractJsonRepository<AuditLogEntry>
        implements AuditLogRepository {

    public JsonAuditLogRepository(JsonFileUtil fileUtil) {
        super(fileUtil, "audit-log.json", AuditLogEntry[].class);
    }

    @Override
    public AuditLogEntry save(AuditLogEntry entry) {
        List<AuditLogEntry> all = readAllInternal();
        all.add(entry);
        writeAllInternal(all);
        return entry;
    }

    @Override
    public List<AuditLogEntry> findAll() {
        return new ArrayList<>(readAllInternal());
    }
}
