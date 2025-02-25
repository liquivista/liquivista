package com.document.management.service.service;

import com.document.management.service.model.DmsModel;
import com.document.management.service.repository.DmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DmsServiceImpl implements DmsService{

    private final DmsRepository dmsRepository;

    public DmsModel uploadDocument(MultipartFile file, String uploadedBy, String documentCategory, String serviceName) {
        try {
            log.info("Start uploading document - Uploaded by: {}, Document Category: {}, Service Name: {}", uploadedBy, documentCategory, serviceName);

            DmsModel dmsModel = DmsModel.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .fileData(file.getBytes())
                    .uploadDate(LocalDateTime.now())
                    .uploadedBy(uploadedBy)
                    .documentCategory(documentCategory)
                    .serviceName(serviceName)
                    .status("active")
                    .build();

            DmsModel savedDocument = dmsRepository.save(dmsModel);
            log.info("Document uploaded successfully with DMS ID: {}", savedDocument.getDmsId());

            return savedDocument;
        } catch (Exception e) {
            log.error("Unexpected error occurred during file upload: {}", e.getMessage());
            throw new RuntimeException("An unexpected error occurred during the file upload.");
        }
    }


    public boolean deleteDocument(String dmsId) {
        log.info("Start deleting document with DMS ID: {}", dmsId);

        DmsModel document = dmsRepository.findByDmsIdAndStatus(dmsId,"active").orElse(null);
        if (document != null) {
            document.setStatus("inactive");
            dmsRepository.save(document);
            log.info("Document with DMS ID: {} Deleted Successfully", dmsId);
            return true;
        }

        log.warn("Document with DMS ID: {} Deleted Successfully", dmsId);
        return false;
    }

    public DmsModel getDocumentById(String dmsId) {
        log.info("Fetching document with DMS ID: {}", dmsId);

        DmsModel document = dmsRepository.findByDmsIdAndStatus(dmsId,"active").orElse(null);
        if (document != null) {
            log.info("Document with DMS ID: {} retrieved successfully", dmsId);
        } else {
            log.warn("Document with DMS ID: {} not found", dmsId);
        }
        return document;
    }

    @Override
    public List<DmsModel> getAllDocuments() {
        log.info("Fetching all documents");
        return dmsRepository.findAll();
    }
}
