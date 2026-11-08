package com.hms.cluster_management.service;

import com.hms.cluster_management.dto.HospitalRequest;
import com.hms.cluster_management.dto.HospitalResponse;
import com.hms.cluster_management.model.Hospital;
import com.hms.cluster_management.repository.ClusterRepository;
import com.hms.cluster_management.repository.HospitalRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ClusterRepository clusterRepository;

    public HospitalService(
            HospitalRepository hospitalRepository,
            ClusterRepository clusterRepository) {

        this.hospitalRepository = hospitalRepository;
        this.clusterRepository = clusterRepository;
    }

    // Convert Entity to Response DTO
    private HospitalResponse toResponse(Hospital hospital) {
        return new HospitalResponse(
                hospital.getId(),
                hospital.getHospitalName(),
                hospital.getHospitalCode(),
                hospital.getLocation(),
                hospital.getStatus(),
                hospital.getClusterId()
        );
    }

    // Get all hospitals
    public List<HospitalResponse> getAllHospitals() {
        return hospitalRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get hospital by ID
    public HospitalResponse getHospitalById(String id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found"));

        return toResponse(hospital);
    }

    // Create hospital
    public HospitalResponse createHospital(HospitalRequest request) {

        // Check whether cluster exists
        if (!clusterRepository.existsById(request.getClusterId())) {
            throw new RuntimeException("Cluster not found");
        }

        // Check duplicate hospital code
        if (hospitalRepository.existsByHospitalCode(request.getHospitalCode())) {
            throw new IllegalArgumentException("Hospital code already exists");
        }

        Hospital hospital = new Hospital(
            request.getHospitalName(),
            request.getHospitalCode(),
            request.getLocation(),
            request.getStatus() == null
                    ? "ACTIVE"
                    : request.getStatus(),
            request.getClusterId(),
            generateDatabaseName(request.getHospitalCode())
        );

        Hospital savedHospital = hospitalRepository.save(hospital);

        return toResponse(savedHospital);
    }

    // Update hospital
    public HospitalResponse updateHospital(
            String id,
            HospitalRequest request) {

        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Hospital not found"));

        if (!clusterRepository.existsById(request.getClusterId())) {
            throw new RuntimeException("Cluster not found");
        }

        if (!hospital.getHospitalCode().equals(request.getHospitalCode())
                && hospitalRepository.existsByHospitalCode(request.getHospitalCode())) {
            throw new IllegalArgumentException("Hospital code already exists");
        }

        hospital.setHospitalName(request.getHospitalName());
        hospital.setHospitalCode(request.getHospitalCode());
        hospital.setLocation(request.getLocation());
        hospital.setStatus(request.getStatus() == null
                ? hospital.getStatus()
                : request.getStatus());
        hospital.setClusterId(request.getClusterId());

        Hospital updatedHospital = hospitalRepository.save(hospital);

        return toResponse(updatedHospital);
    }

    // Delete hospital
    public void deleteHospital(String id) {

        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital not found");
        }

        hospitalRepository.deleteById(id);
    }

    public List<HospitalResponse> getHospitalsByCluster(String clusterId) {

        if (!clusterRepository.existsById(clusterId)) {
            throw new RuntimeException("Cluster not found");
        }

        return hospitalRepository.findByClusterId(clusterId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private String generateDatabaseName(String hospitalCode) {

        String sanitizedCode = hospitalCode
                .toLowerCase()
                .replaceAll("[^a-z0-9]", "");

        return "hms_" + sanitizedCode;
    }
}