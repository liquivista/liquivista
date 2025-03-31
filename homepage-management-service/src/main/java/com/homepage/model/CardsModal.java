package com.homepage.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "home_cards")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardsModal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_product_id")
    private Long cardProductId;

    @Column(name = "card_product_name")
    private String cardProductName;

    @Column(name = "card_product_desc")
    private String cardProductDesc;

    @Column(name = "card_image_dms_id")
    private String cardImageDmsId;

}
