package com.hms.cluster_management.service;

import com.hms.cluster_management.dto.ClusterRequest;
import com.hms.cluster_management.dto.ClusterResponse;
import com.hms.cluster_management.model.Cluster;
import com.hms.cluster_management.repository.ClusterRepository;
import com.hms.cluster_management.repository.HospitalRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterService {

    private final ClusterRepository clusterRepository;
    private final HospitalRepository hospitalRepository;

    public ClusterService(ClusterRepository clusterRepository, HospitalRepository hospitalRepository) {
        this.clusterRepository = clusterRepository;
        this.hospitalRepository = hospitalRepository;
    }

    // Convert Entity to Response DTO
    private ClusterResponse toResponse(Cluster cluster) {

        return new ClusterResponse(
                cluster.getId(),
                cluster.getClusterName(),
                cluster.getClusterCode(),
                cluster.getLocation(),
                cluster.getStatus()
        );
    }

    // Get all clusters
    public List<ClusterResponse> getAllClusters() {

        return clusterRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Get cluster by ID
    public ClusterResponse getClusterById(String id) {

        Cluster cluster = clusterRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cluster not found"));

        return toResponse(cluster);
    }

    // Create cluster
    public ClusterResponse createCluster(ClusterRequest request) {

        if (clusterRepository.existsByClusterCode(
                request.getClusterCode())) {

            throw new IllegalArgumentException(
                    "Cluster code already exists");
        }

        Cluster cluster = new Cluster(
                request.getClusterName(),
                request.getClusterCode(),
                request.getLocation(),
                request.getStatus() == null
                        ? "ACTIVE"
                        : request.getStatus()
        );

        Cluster savedCluster = clusterRepository.save(cluster);

        return toResponse(savedCluster);
    }

    // Update cluster
    public ClusterResponse updateCluster(
            String id,
            ClusterRequest request) {

        Cluster cluster = clusterRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cluster not found"));

        boolean codeUsedByAnotherCluster =
                clusterRepository.existsByClusterCode(
                        request.getClusterCode())
                && !cluster.getClusterCode().equals(
                        request.getClusterCode());

        if (codeUsedByAnotherCluster) {
            throw new IllegalArgumentException(
                    "Cluster code already exists");
        }

        cluster.setClusterName(request.getClusterName());
        cluster.setClusterCode(request.getClusterCode());
        cluster.setLocation(request.getLocation());
        cluster.setStatus(request.getStatus());

        Cluster updatedCluster = clusterRepository.save(cluster);

        return toResponse(updatedCluster);
    }

    // Delete cluster
    public void deleteCluster(String id) {

        if (!clusterRepository.existsById(id)) {
            throw new RuntimeException("Cluster not found");
        }

        if (hospitalRepository.existsByClusterId(id)) {
            throw new IllegalArgumentException(
                    "Cannot delete cluster because hospitals are assigned to it"
            );
        }

        clusterRepository.deleteById(id);
    }
}