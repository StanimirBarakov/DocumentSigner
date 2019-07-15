package com.axway.academy.model.dao;

import com.axway.academy.model.dto.UpdateDocumentDTO;
import com.axway.academy.model.entity.Document;
import com.axway.academy.model.exception.DocumentTypeException;

import java.util.List;

public interface AdminDao {

    List<Document> getAllFiles();

    void updateDocumentType(UpdateDocumentDTO documentDTO) throws DocumentTypeException;

}
