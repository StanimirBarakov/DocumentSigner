package com.axway.academy.service;

import com.axway.academy.model.dto.DocumentDTO;
import com.axway.academy.model.dto.UpdateDocumentDTO;
import com.axway.academy.model.exception.DocumentTypeException;

import java.util.List;

public interface AdminService {
    List<DocumentDTO> getAllFiles();
    void updateDocumentType(UpdateDocumentDTO documentDTO) throws DocumentTypeException;
}
