package com.hms.cluster_management.dto;

import jakarta.validation.constraints.NotBlank;

public class HospitalRequest {

    @NotBlank(message = "Hospital name is required")
    private String hospitalName;

    @NotBlank(message = "Hospital code is required")
    private String hospitalCode;

    @NotBlank(message = "Location is required")
    private String location;

    private String status;

    @NotBlank(message = "Cluster ID is required")
    private String clusterId;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }
}