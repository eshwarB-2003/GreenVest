package com.greenvest.repository;

import com.greenvest.model.AuditLogEntry;

import java.util.List;

public interface AuditLogRepository {

    AuditLogEntry save(AuditLogEntry entry);

    List<AuditLogEntry> findAll();
}
