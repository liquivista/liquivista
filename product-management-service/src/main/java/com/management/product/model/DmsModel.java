package com.management.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DmsModel {

        private MultipartFile file;
        private String uploadedBy;
        private String serviceName;
        private String documentCategory;

}
