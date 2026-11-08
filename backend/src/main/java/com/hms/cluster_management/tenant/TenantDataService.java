package com.hms.cluster_management.tenant;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class TenantDataService {

    private final TenantMongoTemplateProvider tenantProvider;

    public TenantDataService(
            TenantMongoTemplateProvider tenantProvider) {
        this.tenantProvider = tenantProvider;
    }

    public Document saveTestRecord(String hospitalId, String message) {

        MongoTemplate template =
                tenantProvider.getTemplateForHospital(hospitalId);

        Document document = new Document()
                .append("message", message)
                .append("createdAt", Instant.now().toString());

        return template.getCollection("tenant_test")
                .insertOne(document)
                .wasAcknowledged()
                ? document.append("status", "saved")
                : document;
    }

    public List<Document> getTestRecords(String hospitalId) {

        MongoTemplate template =
                tenantProvider.getTemplateForHospital(hospitalId);

        return template.getCollection("tenant_test")
                .find()
                .into(new ArrayList<>());
    }
}