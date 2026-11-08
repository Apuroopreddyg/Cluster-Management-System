package com.hms.cluster_management.controller;

import com.hms.cluster_management.tenant.TenantDataService;

import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals/{hospitalId}/tenant-test")
@CrossOrigin(origins = "http://localhost:4200")
public class TenantTestController {

    private final TenantDataService tenantDataService;

    public TenantTestController(
            TenantDataService tenantDataService) {
        this.tenantDataService = tenantDataService;
    }

    @PostMapping
    public Document saveTestRecord(
            @PathVariable String hospitalId,
            @RequestBody TestRequest request) {

        return tenantDataService.saveTestRecord(
                hospitalId,
                request.message()
        );
    }

    @GetMapping
    public List<Document> getTestRecords(
            @PathVariable String hospitalId) {

        return tenantDataService.getTestRecords(hospitalId);
    }

    public record TestRequest(String message) {}
}