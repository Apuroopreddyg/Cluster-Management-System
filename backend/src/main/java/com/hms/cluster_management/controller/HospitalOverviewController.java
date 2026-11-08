package com.hms.cluster_management.controller;

import com.hms.cluster_management.dto.HospitalOverviewResponse;
import com.hms.cluster_management.service.HospitalOverviewService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitals/{hospitalId}/overview")
@CrossOrigin(origins = "http://localhost:4200")
public class HospitalOverviewController {

    private final HospitalOverviewService overviewService;

    public HospitalOverviewController(
            HospitalOverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @GetMapping
    public HospitalOverviewResponse getOverview(
            @PathVariable String hospitalId) {

        return overviewService.getOverview(hospitalId);
    }
}