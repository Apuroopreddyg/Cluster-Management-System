package com.hms.cluster_management.dto;

public record HospitalOverviewResponse(
        String hospitalId,
        String hospitalName,
        long totalOPRecords,
        long totalMedicines
) {}