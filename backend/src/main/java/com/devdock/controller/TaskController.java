package com.devdock.controller;

import com.devdock.dto.TaskRequestDTO;
import com.devdock.dto.TaskResponseDTO;
import com.devdock.dto.TaskStatusUpdateDTO;
import com.devdock.entity.Priority;
import com.devdock.entity.TaskStatus;
import com.devdock.security.UserPrincipal;
import com.devdock.service.TaskService;
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
@Tag(name = "Tasks", description = "CRUD and status tracking for project tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/api/v1/projects/{projectId}/tasks")
    @Operation(summary = "Get all tasks for a specific project")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByProject(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(userPrincipal, projectId));
    }

    @PostMapping("/api/v1/projects/{projectId}/tasks")
    @Operation(summary = "Create a task scoped to a project")
    public ResponseEntity<TaskResponseDTO> createTaskForProject(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequestDTO request) {
        TaskResponseDTO created = taskService.createTask(userPrincipal, projectId, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/tasks")
    @Operation(summary = "Get all tasks across all projects with filters")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) Long projectId,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(taskService.getAllTasks(userPrincipal, projectId, status, priority, search));
    }

    @GetMapping("/api/v1/tasks/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(userPrincipal, id));
    }

    @PostMapping("/api/v1/tasks")
    @Operation(summary = "Create a task (with projectId in body)")
    public ResponseEntity<TaskResponseDTO> createTask(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody TaskRequestDTO request) {
        TaskResponseDTO created = taskService.createTask(userPrincipal, request.getProjectId(), request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/tasks/{id}")
    @Operation(summary = "Update task details")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO request) {
        return ResponseEntity.ok(taskService.updateTask(userPrincipal, id, request));
    }

    @PatchMapping("/api/v1/tasks/{id}/status")
    @Operation(summary = "Quick update task status (e.g. for Kanban drag-and-drop)")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusUpdateDTO request) {
        return ResponseEntity.ok(taskService.updateTaskStatus(userPrincipal, id, request.getStatus()));
    }

    @DeleteMapping("/api/v1/tasks/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Map<String, String>> deleteTask(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long id) {
        taskService.deleteTask(userPrincipal, id);
        return ResponseEntity.ok(Collections.singletonMap("message", "Task deleted successfully"));
    }
}
