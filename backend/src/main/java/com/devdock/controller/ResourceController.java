package com.devdock.controller;

import com.devdock.dto.ResourceRequestDTO;
import com.devdock.dto.ResourceResponseDTO;
import com.devdock.dto.TagDTO;
import com.devdock.entity.ResourceType;
import com.devdock.security.UserPrincipal;
import com.devdock.service.ResourceService;
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
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Resources & Tags", description = "Centralized developer bookmarks, learning links, and tags")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/api/v1/resources")
    @Operation(summary = "List resources with search and filter support")
    public ResponseEntity<List<ResourceResponseDTO>> getAllResources(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) ResourceType type,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Long taskId,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(resourceService.getAllResources(userPrincipal, type, tag, taskId, search));
    }

    @GetMapping("/api/v1/resources/{id}")
    @Operation(summary = "Get resource by ID")
    public ResponseEntity<ResourceResponseDTO> getResourceById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        return ResponseEntity.ok(resourceService.getResourceById(userPrincipal, id));
    }

    @PostMapping("/api/v1/resources")
    @Operation(summary = "Create a new resource link with tags and optional task association")
    public ResponseEntity<ResourceResponseDTO> createResource(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ResourceRequestDTO request) {
        ResourceResponseDTO created = resourceService.createResource(userPrincipal, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/resources/{id}")
    @Operation(summary = "Update an existing resource link")
    public ResponseEntity<ResourceResponseDTO> updateResource(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @Valid @RequestBody ResourceRequestDTO request) {
        return ResponseEntity.ok(resourceService.updateResource(userPrincipal, id, request));
    }

    @DeleteMapping("/api/v1/resources/{id}")
    @Operation(summary = "Delete a resource link")
    public ResponseEntity<Map<String, String>> deleteResource(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        resourceService.deleteResource(userPrincipal, id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Resource deleted successfully"));
    }

    @GetMapping("/api/v1/tags")
    @Operation(summary = "List all tags used by the current user")
    public ResponseEntity<List<TagDTO>> getAllTags(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(resourceService.getAllTags(userPrincipal));
    }
}
