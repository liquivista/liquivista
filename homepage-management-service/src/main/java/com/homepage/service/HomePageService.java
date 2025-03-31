package com.homepage.service;

import com.homepage.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HomePageService {
    String addCards(CardsDto cardsDto, MultipartFile file);

    List<CardsDto> getAllCards();

    ResponseEntity<?> downloadDocument(String dmsId);
}
