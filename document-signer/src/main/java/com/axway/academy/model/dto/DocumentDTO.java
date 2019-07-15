package com.axway.academy.model.dto;

import com.axway.academy.model.entity.DecisionType;

public class DocumentDTO {

    private Long id;
    private String name;
    private DecisionType decisionType;

    public DocumentDTO(Long id, String name, DecisionType decisionType) {
        this.id = id;
        this.name = name;
        this.decisionType = decisionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DecisionType getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(DecisionType decisionType) {
        this.decisionType = decisionType;
    }
}
