package com.devdock.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags", indexes = {
    @Index(name = "idx_tag_name", columnList = "name", unique = true)
})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 20)
    private String color;

    @ManyToMany(mappedBy = "tags")
    private Set<Resource> resources = new HashSet<>();

    public Tag() {}

    public Tag(String name, String color) {
        this.name = name != null ? name.trim().toLowerCase() : "";
        this.color = color;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name != null ? name.trim().toLowerCase() : ""; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Set<Resource> getResources() { return resources; }
    public void setResources(Set<Resource> resources) { this.resources = resources; }
}
