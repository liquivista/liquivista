package com.homepage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DmsModel {

        private MultipartFile file;
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
