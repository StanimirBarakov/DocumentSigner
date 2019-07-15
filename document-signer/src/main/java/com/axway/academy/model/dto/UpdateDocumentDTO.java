package com.axway.academy.model.dto;

import com.axway.academy.model.entity.DecisionType;

public class UpdateDocumentDTO {

    private Long id;
    private DecisionType decisionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DecisionType getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(DecisionType decisionType) {
        this.decisionType = decisionType;
    }
}
