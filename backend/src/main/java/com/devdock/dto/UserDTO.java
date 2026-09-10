package com.devdock.dto;

import com.devdock.entity.Role;
import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String avatarUrl;
    private String bio;
    private LocalDateTime createdAt;

    public UserDTO() {}
    public UserDTO(Long id, String name, String email, Role role, String avatarUrl, String bio, LocalDateTime createdAt) {
        this.id = id; this.name = name; this.email = email; this.role = role;
        this.avatarUrl = avatarUrl; this.bio = bio; this.createdAt = createdAt;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
