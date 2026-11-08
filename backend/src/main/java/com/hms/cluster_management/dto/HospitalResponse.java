package com.hms.cluster_management.dto;

public record HospitalResponse(
        String id,
        String hospitalName,
        String hospitalCode,
        String location,
        String status,
        String clusterId
) {
}