package com.hms.cluster_management.repository;

import com.hms.cluster_management.model.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HospitalRepository
        extends MongoRepository<Hospital, String> {

    List<Hospital> findByClusterId(String clusterId);

    boolean existsByClusterId(String clusterId);
    
    boolean existsByHospitalCode(String hospitalCode);
}