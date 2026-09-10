package com.devdock.dto;

import com.devdock.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class TaskStatusUpdateDTO {
    @NotNull(message = "Status is required")
    private TaskStatus status;

    public TaskStatusUpdateDTO() {}
    public TaskStatusUpdateDTO(TaskStatus status) { this.status = status; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
}
