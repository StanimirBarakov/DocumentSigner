package com.axway.academy.model.Mapper;

import com.axway.academy.model.dto.DocumentDTO;
import com.axway.academy.model.entity.Document;

public interface DocumentMapper {
    DocumentDTO toDTO(Document document);
}
