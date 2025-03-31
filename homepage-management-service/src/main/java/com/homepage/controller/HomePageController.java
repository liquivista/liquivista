package com.homepage.controller;

import com.homepage.dto.CardsDto;
import com.homepage.service.HomePageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/homepage-management")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class HomePageController {

    private final HomePageService homePageService;

    @PostMapping("/add-cards")
    public ResponseEntity<String> addCards(@RequestParam("cardProductName") String cardProductName,
                                           @RequestParam("cardProductDesc") String cardProductDesc,
                                           @RequestParam("file") MultipartFile file){
        CardsDto cardsDto = new CardsDto(cardProductName,cardProductDesc,null);
        String response = homePageService.addCards(cardsDto,file);

        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User registration failed. Please try again.", HttpStatus.BAD_GATEWAY);
    }

    @GetMapping("/get-all-cards")
    public ResponseEntity<List<CardsDto>> getAllCards() {
        List<CardsDto> cardsDtoList = homePageService.getAllCards();
        if (cardsDtoList != null && !cardsDtoList.isEmpty()) {
            return new ResponseEntity<>(cardsDtoList, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/download-document/{dmsId}")
    public ResponseEntity<?> downloadDocument(@PathVariable String dmsId){
        return homePageService.downloadDocument(dmsId);
    }
}
