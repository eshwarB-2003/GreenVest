package com.greenvest.patterns.interceptor;

import com.greenvest.model.AuditLogEntry;
import com.greenvest.repository.AuditLogRepository;

public class AdminActionInterceptor implements Interceptor {

    private final AuditLogRepository auditRepository;
    private final String adminId;
    private final String actionDescription;

    public AdminActionInterceptor(AuditLogRepository auditRepository, String adminId, String actionDescription) {
        this.auditRepository = auditRepository;
        this.adminId = adminId;
        this.actionDescription = actionDescription;
    }

    @Override
    public void before() {
        // could log "start", left empty for now
    }

    @Override
    public void after() {
        AuditLogEntry entry = new AuditLogEntry(adminId, "ADMIN_ACTION", actionDescription);
        auditRepository.save(entry);
    }
}
