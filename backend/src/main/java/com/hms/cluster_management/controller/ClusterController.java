package com.hms.cluster_management.controller;

import com.hms.cluster_management.dto.ClusterRequest;
import com.hms.cluster_management.dto.ClusterResponse;
import com.hms.cluster_management.dto.HospitalResponse;
import com.hms.cluster_management.service.ClusterService;
import com.hms.cluster_management.service.HospitalService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clusters")
@CrossOrigin(origins = "http://localhost:4200")
public class ClusterController {

    private final ClusterService clusterService;
    private final HospitalService hospitalService;

    public ClusterController(ClusterService clusterService, HospitalService hospitalService) {
        this.clusterService = clusterService;
        this.hospitalService = hospitalService;
    }

    // GET all clusters
    @GetMapping
    public ResponseEntity<List<ClusterResponse>> getAllClusters() {

        return ResponseEntity.ok(
                clusterService.getAllClusters());
    }

    // GET cluster by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClusterResponse> getClusterById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                clusterService.getClusterById(id));
    }

    // CREATE cluster
    @PostMapping
    public ResponseEntity<ClusterResponse> createCluster(
            @Valid @RequestBody ClusterRequest request) {

        ClusterResponse response =
                clusterService.createCluster(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    // UPDATE cluster
    @PutMapping("/{id}")
    public ResponseEntity<ClusterResponse> updateCluster(
            @PathVariable String id,
            @Valid @RequestBody ClusterRequest request) {

        return ResponseEntity.ok(
                clusterService.updateCluster(id, request));
    }

    // DELETE cluster
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCluster(
            @PathVariable String id) {

        clusterService.deleteCluster(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/hospitals")
    public ResponseEntity<List<HospitalResponse>> getHospitalsByCluster(
            @PathVariable String id) {

        return ResponseEntity.ok(
                hospitalService.getHospitalsByCluster(id)
        );
    }
}