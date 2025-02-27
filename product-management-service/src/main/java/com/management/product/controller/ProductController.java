package com.management.product.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.product.Client.UserFeign;
import com.management.product.dto.ProductRequestDto;
import com.management.product.dto.ProductResponseDto;
import com.management.product.dto.UserResponseDto;
import com.management.product.model.DmsModel;
import com.management.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product-management")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    private final UserFeign userFeign;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(
            @RequestPart("product") String productRequestDtoJson,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userID) {

        ObjectMapper objectMapper = new ObjectMapper();
        ProductRequestDto productRequestDto = null;
        try {
            productRequestDto = objectMapper.readValue(productRequestDtoJson, ProductRequestDto.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing product JSON: {}", e.getMessage());
            return new ResponseEntity<>("Invalid product data format", HttpStatus.BAD_REQUEST);
        }
        UserResponseDto user;
        try {
            user = userFeign.getUser(userID).getBody();
        } catch (Exception e) {
            log.error("Error fetching user with ID {}: {}", userID, e.getMessage());
            return new ResponseEntity<>("User not found or unable to fetch user details", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (user != null) {
            DmsModel dmsModel = new DmsModel();
            dmsModel.setFile(file);
            dmsModel.setServiceName("PRODUCT_MANAGEMENT_SERVICE");
            dmsModel.setUploadedBy(userID.toString());
            dmsModel.setDocumentCategory("Payment Invoice");

            String response = productService.addProduct(productRequestDto, dmsModel);

            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                log.error("Product creation failed for UserID: {}", userID);
                return new ResponseEntity<>("Product creation failed. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.warn("User with ID {} not found", userID);
            return new ResponseEntity<>("Product creation failed. User Not Found With UserID: " + userID, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();

        if (products != null && !products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/get-product-by-id")
    public ResponseEntity<ProductResponseDto> getProductById(@RequestParam Long productId) {
        ProductResponseDto product = productService.getProductById(productId);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/download-document/{dmsId}")
    public ResponseEntity<?> downloadDocument(@PathVariable String dmsId){
        return productService.downloadDocument(dmsId);
    }
}
