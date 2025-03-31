package com.homepage.service;

import com.homepage.client.DmsFeign;
import com.homepage.dto.CardsDto;
import com.homepage.model.CardsModal;
import com.homepage.model.DmsModel;
import com.homepage.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomePageServiceImpl implements HomePageService{

    private final CardsRepository cardsRepository;

    private final DmsFeign dmsFeign;

    @Override
    public String addCards(CardsDto cardsDto, MultipartFile file) {
        ResponseEntity<String> cardImageDmsId = dmsFeign.uploadDocument(file, "admin", "Home_Cards", "PRODUCT_MANAGEMENT_SERVICE");

        if (cardImageDmsId.getStatusCode().is2xxSuccessful()) {
            log.info("addCards CardImageDmsId: {}", cardImageDmsId.getBody());

            CardsModal cardsModal = CardsModal.builder()
                    .cardProductName(cardsDto.cardProductName())
                    .cardProductDesc(cardsDto.cardProductDesc())
                    .cardImageDmsId(cardImageDmsId.getBody())
                    .build();

            CardsModal savedCard = cardsRepository.save(cardsModal);
            return String.valueOf(savedCard.getCardProductId());
        } else {
            log.error("Failed to upload file. Status Code: {}", cardImageDmsId.getBody());
            return "Error: Failed to upload file. Please try again."+cardImageDmsId.getBody();
        }
    }

    @Override
    public List<CardsDto> getAllCards() {
        List<CardsModal> cardsModalList = cardsRepository.findAll();
        log.info("Inside getAllCards");
        //cardsModalList.forEach(card -> log.info("Card: {}", mapToCardDto(card)));
        return cardsModalList.stream()
                .map(this::mapToCardDto)
                .toList();
    }

    @Override
    public ResponseEntity<?> downloadDocument(String dmsId) {
        DmsModel document = dmsFeign.downloadDocument(dmsId);
        //log.info("Inside Download Document User Service with DMS Id");
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

    private CardsDto mapToCardDto(CardsModal card) {
        return new CardsDto(
                card.getCardProductName(),
                card.getCardProductDesc(),
                card.getCardImageDmsId()
        );
    }
}
