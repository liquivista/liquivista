package com.document.management.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Document(value = "document-management-service")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DmsModel {

    @Id
    private String dmsId;
    private String fileName;
    private String fileType;
    private long fileSize;
    private byte[] fileData;
    private LocalDateTime uploadDate;
    private String uploadedBy;
    private String serviceName;
    private String documentCategory;
    private String status;
}
