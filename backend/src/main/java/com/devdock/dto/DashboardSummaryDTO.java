package com.devdock.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardSummaryDTO {
    private long totalProjects;
    private long totalTasks;
    private long completedTasks;
    private long inProgressTasks;
    private long todoTasks;
    private long totalResources;
    private int overallProgressPercentage;

    private List<ProjectResponseDTO> recentProjects = new ArrayList<>();
    private List<TaskResponseDTO> upcomingTasks = new ArrayList<>();
    private List<ResourceResponseDTO> recentResources = new ArrayList<>();

    private Map<String, Long> tasksByStatus = new HashMap<>();
    private Map<String, Long> resourcesByType = new HashMap<>();

    public DashboardSummaryDTO() {}
    public long getTotalProjects() { return totalProjects; }
    public void setTotalProjects(long totalProjects) { this.totalProjects = totalProjects; }
    public long getTotalTasks() { return totalTasks; }
    public void setTotalTasks(long totalTasks) { this.totalTasks = totalTasks; }
    public long getCompletedTasks() { return completedTasks; }
    public void setCompletedTasks(long completedTasks) { this.completedTasks = completedTasks; }
    public long getInProgressTasks() { return inProgressTasks; }
    public void setInProgressTasks(long inProgressTasks) { this.inProgressTasks = inProgressTasks; }
    public long getTodoTasks() { return todoTasks; }
    public void setTodoTasks(long todoTasks) { this.todoTasks = todoTasks; }
    public long getTotalResources() { return totalResources; }
    public void setTotalResources(long totalResources) { this.totalResources = totalResources; }
    public int getOverallProgressPercentage() { return overallProgressPercentage; }
    public void setOverallProgressPercentage(int overallProgressPercentage) { this.overallProgressPercentage = overallProgressPercentage; }
    public List<ProjectResponseDTO> getRecentProjects() { return recentProjects; }
    public void setRecentProjects(List<ProjectResponseDTO> recentProjects) { this.recentProjects = recentProjects; }
    public List<TaskResponseDTO> getUpcomingTasks() { return upcomingTasks; }
    public void setUpcomingTasks(List<TaskResponseDTO> upcomingTasks) { this.upcomingTasks = upcomingTasks; }
    public List<ResourceResponseDTO> getRecentResources() { return recentResources; }
    public void setRecentResources(List<ResourceResponseDTO> recentResources) { this.recentResources = recentResources; }
    public Map<String, Long> getTasksByStatus() { return tasksByStatus; }
    public void setTasksByStatus(Map<String, Long> tasksByStatus) { this.tasksByStatus = tasksByStatus; }
    public Map<String, Long> getResourcesByType() { return resourcesByType; }
    public void setResourcesByType(Map<String, Long> resourcesByType) { this.resourcesByType = resourcesByType; }
}
