package com.document.management.service.dto;

import java.time.LocalDateTime;

public record DmsDto(String dmsId,
                     String fileName,
                     String fileType,
                     long fileSize,
                     LocalDateTime uploadDate,
                     String uploadedBy,
                     String serviceName,
                     String documentCategory,
                     String status) {}
