package com.hms.cluster_management.dto;

public record ClusterResponse(
        String id,
        String clusterName,
        String clusterCode,
        String location,
        String status
) {
}