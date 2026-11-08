package com.hms.cluster_management.service;

import com.hms.cluster_management.dto.HospitalOverviewResponse;
import com.hms.cluster_management.model.Hospital;
import com.hms.cluster_management.repository.HospitalRepository;
import com.hms.cluster_management.tenant.TenantMongoTemplateProvider;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class HospitalOverviewService {

    private final HospitalRepository hospitalRepository;
    private final TenantMongoTemplateProvider tenantProvider;

    public HospitalOverviewService(
            HospitalRepository hospitalRepository,
            TenantMongoTemplateProvider tenantProvider) {

        this.hospitalRepository = hospitalRepository;
        this.tenantProvider = tenantProvider;
    }

    public HospitalOverviewResponse getOverview(String hospitalId) {

        // Find hospital in central database
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found"));

        // Get the selected hospital's database
        MongoTemplate template =
                tenantProvider.getTemplateForHospital(hospitalId);

        // Count OP records
        long totalOPRecords = template.collectionExists("op_records")
                ? template.getCollection("op_records").countDocuments()
                : 0;

        // Count medicines
        long totalMedicines = template.collectionExists("medicines")
                ? template.getCollection("medicines").countDocuments()
                : 0;

        return new HospitalOverviewResponse(
                hospital.getId(),
                hospital.getHospitalName(),
                totalOPRecords,
                totalMedicines
        );
    }
}