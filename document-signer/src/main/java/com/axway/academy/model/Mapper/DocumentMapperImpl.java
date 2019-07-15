package com.axway.academy.model.Mapper;

import com.axway.academy.model.dto.DocumentDTO;
import com.axway.academy.model.entity.Document;

public class DocumentMapperImpl implements DocumentMapper {
    @Override
    public DocumentDTO toDTO(Document document) {
        return new DocumentDTO(document.getId(),document.getName(),document.getDecisionType());
    }
}
