package com.document.management.service.service;

import com.document.management.service.model.DmsModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DmsService {
    DmsModel uploadDocument(MultipartFile file, String uploadedBy, String documentCategory, String serviceName) throws IOException;

    boolean deleteDocument(String dmsId);

    DmsModel getDocumentById(String dmsId);

    List<DmsModel> getAllDocuments();
}
