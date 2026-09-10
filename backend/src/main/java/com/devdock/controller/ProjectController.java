package com.devdock.controller;

import com.devdock.dto.ProjectRequestDTO;
import com.devdock.dto.ProjectResponseDTO;
import com.devdock.entity.ProjectStatus;
import com.devdock.security.UserPrincipal;
import com.devdock.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Projects", description = "CRUD operations for user coding projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @Operation(summary = "List all projects for current user", description = "Filterable by status and search keyword")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(projectService.getAllProjects(userPrincipal, status, search));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project details by ID")
    public ResponseEntity<ProjectResponseDTO> getProjectById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(userPrincipal, id));
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ProjectResponseDTO> createProject(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ProjectRequestDTO request) {
        ProjectResponseDTO created = projectService.createProject(userPrincipal, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing project")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequestDTO request) {
        return ResponseEntity.ok(projectService.updateProject(userPrincipal, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a project (cascades to all associated tasks)")
    public ResponseEntity<Map<String, String>> deleteProject(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        projectService.deleteProject(userPrincipal, id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Project deleted successfully"));
    }
}
