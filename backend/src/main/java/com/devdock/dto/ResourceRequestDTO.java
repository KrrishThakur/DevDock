package com.devdock.dto;

import com.devdock.entity.ResourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ResourceRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 250, message = "Title cannot exceed 250 characters")
    private String title;

    @NotBlank(message = "URL is required")
    private String url;

    private ResourceType type = ResourceType.OTHER;
    private String notes;
    private Long taskId;
    private List<String> tags = new ArrayList<>();

    public ResourceRequestDTO() {}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public ResourceType getType() { return type; }
    public void setType(ResourceType type) { this.type = type; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}
