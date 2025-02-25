package com.management.product.Client;

import com.management.product.model.DmsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "dmsFeign" , url = "http://localhost:9801/dms")
public interface DmsFeign {

    @PostMapping(value = "/upload-document-to-dms", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadDocument(
            @RequestPart("file") MultipartFile file,
            @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("documentCategory") String documentCategory,
            @RequestParam("serviceName") String serviceName);

    @PostMapping("/delete-document/{dmsId}")
    ResponseEntity<String> deleteDocument(@PathVariable String dmsId);

    @GetMapping("/get-document/{dmsId}")
    ResponseEntity<?> getDocument(@PathVariable String dmsId);

    @GetMapping("/download-document/{dmsId}")
    public DmsModel downloadDocument(@PathVariable String dmsId);
}
