package com.hms.cluster_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitals")
public class Hospital {

    @Id
    private String id;

    private String hospitalName;
    private String hospitalCode;
    private String location;
    private String status;
    private String clusterId;
    private String databaseName;

    public Hospital() {
    }

    public Hospital(String hospitalName, String hospitalCode,
                   String location, String status, String clusterId, String databaseName) {
        this.hospitalName = hospitalName;
        this.hospitalCode = hospitalCode;
        this.location = location;
        this.status = status;
        this.clusterId = clusterId;
        this.databaseName = databaseName;
    }

    public String getId() {
        return id;
    }

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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}