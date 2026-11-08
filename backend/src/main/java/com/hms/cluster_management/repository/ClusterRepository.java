package com.hms.cluster_management.repository;

import com.hms.cluster_management.model.Cluster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClusterRepository
        extends MongoRepository<Cluster, String> {

    boolean existsByClusterCode(String clusterCode);
}