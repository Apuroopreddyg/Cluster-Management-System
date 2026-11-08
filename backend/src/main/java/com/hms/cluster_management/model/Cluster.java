package com.hms.cluster_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clusters")
public class Cluster {

    @Id
    private String id;

    private String clusterName;
    private String clusterCode;
    private String location;
    private String status;

    public Cluster() {
    }

    public Cluster(String clusterName, String clusterCode,
                   String location, String status) {

        this.clusterName = clusterName;
        this.clusterCode = clusterCode;
        this.location = location;
        this.status = status;
    }

    public String getId() {
        return id;
    }

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