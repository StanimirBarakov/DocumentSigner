package com.axway.academy.service;

import com.axway.academy.model.Mapper.DocumentMapper;
import com.axway.academy.model.Mapper.DocumentMapperImpl;
import com.axway.academy.model.dao.AdminDao;
import com.axway.academy.model.dao.AdminDaoImpl;
import com.axway.academy.model.dto.DocumentDTO;
import com.axway.academy.model.dto.UpdateDocumentDTO;
import com.axway.academy.model.entity.DecisionType;
import com.axway.academy.model.exception.DocumentTypeException;

import java.util.List;
import java.util.stream.Collectors;

public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao = new AdminDaoImpl();
    private DocumentMapper documentMapper = new DocumentMapperImpl();

    @Override
    public List<DocumentDTO> getAllFiles() {
        return adminDao.getAllFiles().stream()
        .map(document -> documentMapper.toDTO(document))
        .collect(Collectors.toList());
    }

    @Override
    public void updateDocumentType(UpdateDocumentDTO documentDTO) throws DocumentTypeException {
        if(!DecisionType.getTypes().contains(documentDTO.getDecisionType())){
            throw new DocumentTypeException("Invalid DecisionType!");
        }
        adminDao.updateDocumentType(documentDTO);

    }
}
