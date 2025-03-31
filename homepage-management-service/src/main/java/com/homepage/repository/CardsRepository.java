package com.homepage.repository;

import com.homepage.model.CardsModal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<CardsModal, Long> {
}
