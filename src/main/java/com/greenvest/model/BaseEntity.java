package com.greenvest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.greenvest.common.IdGenerator;

import java.time.Instant;

public abstract class BaseEntity {

    protected String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Instant createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Instant updatedAt;

    public BaseEntity() {
        this.id = IdGenerator.newId();
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    protected void touch() {
        this.updatedAt = Instant.now();
    }
}
