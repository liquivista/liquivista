package com.document.management.service.controller;

import com.document.management.service.model.DmsModel;
import com.document.management.service.service.DmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

@RestController
@RequestMapping("/dms")
@RequiredArgsConstructor
public class DmsController {

    private final DmsService dmsService;

    @PostMapping("/upload-document-to-dms")
    public ResponseEntity<String> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("documentCategory") String documentCategory,
            @RequestParam("serviceName") String serviceName) {
        try {
            DmsModel uploadedDocument = dmsService.uploadDocument(file, uploadedBy, documentCategory, serviceName);
            return new ResponseEntity<>(uploadedDocument.getDmsId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete-document/{dmsId}")
    public ResponseEntity<String> deleteDocument(@PathVariable String dmsId) {
        boolean isUpdated = dmsService.deleteDocument(dmsId);
        if (isUpdated) {
            return new ResponseEntity<>("Document status changed to inactive successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-document/{dmsId}")
    public ResponseEntity<?> getDocument(@PathVariable String dmsId) {
        DmsModel document = dmsService.getDocumentById(dmsId);
        if (document != null) {
            String contentType = document.getFileType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            ByteArrayResource resource = new ByteArrayResource(document.getFileData());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=" + document.getFileName())
                    .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                    .contentLength(document.getFileSize())
                    .body(resource);
        }
        return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
    }

}
