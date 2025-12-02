package com.greenvest.model;

import java.time.Instant;

public class AuditLogEntry extends BaseEntity {

    private String actorUserId;
    private String actionType;
    private String description;
    private Instant timestamp = Instant.now();

    public AuditLogEntry() {
    }

    public AuditLogEntry(String actorUserId, String actionType, String description) {
        this.actorUserId = actorUserId;
        this.actionType = actionType;
        this.description = description;
    }

    public String getActorUserId() {
        return actorUserId;
    }

    public String getActionType() {
        return actionType;
    }

    public String getDescription() {
        return description;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
