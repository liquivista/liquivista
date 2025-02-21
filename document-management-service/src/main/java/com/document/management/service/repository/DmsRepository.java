package com.document.management.service.repository;

import com.document.management.service.model.DmsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DmsRepository extends MongoRepository<DmsModel, String> {

    Optional<DmsModel> findByDmsIdAndStatus(String dmsId, String status);
}
