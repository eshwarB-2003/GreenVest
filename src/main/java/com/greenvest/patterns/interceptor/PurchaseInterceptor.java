package com.greenvest.patterns.interceptor;

import com.greenvest.model.AuditLogEntry;
import com.greenvest.repository.AuditLogRepository;

public class PurchaseInterceptor implements Interceptor {

    private final AuditLogRepository auditRepository;
    private final String buyerId;
    private final String listingId;

    public PurchaseInterceptor(AuditLogRepository auditRepository, String buyerId, String listingId) {
        this.auditRepository = auditRepository;
        this.buyerId = buyerId;
        this.listingId = listingId;
    }

    @Override
    public void before() {
        // e.g. could check balance or compliance rules
    }

    @Override
    public void after() {
        AuditLogEntry entry = new AuditLogEntry(
                buyerId,
                "PURCHASE",
                "Buyer " + buyerId + " purchased from listing " + listingId
        );
        auditRepository.save(entry);
    }
}
