package com.devdock.controller;

import com.devdock.dto.DashboardSummaryDTO;
import com.devdock.security.UserPrincipal;
import com.devdock.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Dashboard", description = "Single-screen aggregate summary and metrics")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(summary = "Get aggregated dashboard metrics, progress %, and recent items")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(dashboardService.getDashboardSummary(userPrincipal));
    }
}
