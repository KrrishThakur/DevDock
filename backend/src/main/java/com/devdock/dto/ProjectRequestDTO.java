package com.devdock.dto;

import com.devdock.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectRequestDTO {
    @NotBlank(message = "Project title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;
    private String description;
    private ProjectStatus status = ProjectStatus.IN_PROGRESS;
    private String githubUrl;
    private String liveUrl;

    public ProjectRequestDTO() {}
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public String getLiveUrl() { return liveUrl; }
    public void setLiveUrl(String liveUrl) { this.liveUrl = liveUrl; }
}
