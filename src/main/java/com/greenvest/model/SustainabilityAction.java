package com.greenvest.model;

import java.time.Instant;

public class SustainabilityAction extends BaseEntity {

    private String sellerId;
    private String description;
    private String proofFilePath;

    private SustainabilityActionStatus status = SustainabilityActionStatus.DRAFT;
    private String adminReviewNote;
    private Instant reviewedAt;

    public SustainabilityAction() {
    }

    public SustainabilityAction(String sellerId, String description, String proofFilePath) {
        this.sellerId = sellerId;
        this.description = description;
        this.proofFilePath = proofFilePath;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getDescription() {
        return description;
    }

    public String getProofFilePath() {
        return proofFilePath;
    }

    public SustainabilityActionStatus getStatus() {
        return status;
    }

    public String getAdminReviewNote() {
        return adminReviewNote;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setStatus(SustainabilityActionStatus status) {
        this.status = status;
        touch();
    }

    public void setAdminReviewNote(String adminReviewNote) {
        this.adminReviewNote = adminReviewNote;
        touch();
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
        touch();
    }
}
