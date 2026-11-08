package com.hms.cluster_management.tenant;

import com.hms.cluster_management.model.Hospital;
import com.hms.cluster_management.repository.HospitalRepository;

import com.mongodb.client.MongoClient;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class TenantMongoTemplateProvider {

    private final MongoClient mongoClient;
    private final HospitalRepository hospitalRepository;

    private final ConcurrentMap<String, MongoTemplate> templateCache =
            new ConcurrentHashMap<>();

    public TenantMongoTemplateProvider(
            MongoClient mongoClient,
            HospitalRepository hospitalRepository) {

        this.mongoClient = mongoClient;
        this.hospitalRepository = hospitalRepository;
    }

    public MongoTemplate getTemplateForHospital(String hospitalId) {

        // 1. Find hospital in the central database
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found"));

        // 2. Get its database name
        String databaseName = hospital.getDatabaseName();

        if (databaseName == null || !databaseName.matches("hms_[a-z0-9]+")) {
            throw new IllegalStateException(
                    "Invalid or missing hospital database configuration");
        }

        // 3. Reuse an existing template or create a new one
        return templateCache.computeIfAbsent(
                databaseName,
                name -> new MongoTemplate(mongoClient, name)
        );
    }
}