package com.hms.cluster_management.controller;

import com.hms.cluster_management.dto.HospitalRequest;
import com.hms.cluster_management.dto.HospitalResponse;
import com.hms.cluster_management.service.HospitalService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@CrossOrigin(origins = "http://localhost:4200")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public ResponseEntity<List<HospitalResponse>> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponse> getHospitalById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                hospitalService.getHospitalById(id));
    }

    @PostMapping
    public ResponseEntity<HospitalResponse> createHospital(
            @Valid @RequestBody HospitalRequest request) {

        HospitalResponse response =
                hospitalService.createHospital(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponse> updateHospital(
            @PathVariable String id,
            @Valid @RequestBody HospitalRequest request) {

        return ResponseEntity.ok(
                hospitalService.updateHospital(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(
            @PathVariable String id) {

        hospitalService.deleteHospital(id);

        return ResponseEntity.noContent().build();
    }
}