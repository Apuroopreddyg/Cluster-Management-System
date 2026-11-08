package com.hms.cluster_management.dto;

import jakarta.validation.constraints.NotBlank;

public class ClusterRequest {

    @NotBlank(message = "Cluster name is required")
    private String clusterName;

    @NotBlank(message = "Cluster code is required")
    private String clusterCode;

    @NotBlank(message = "Location is required")
    private String location;

    private String status;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(String clusterCode) {
        this.clusterCode = clusterCode;
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
}