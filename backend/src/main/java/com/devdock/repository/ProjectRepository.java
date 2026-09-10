package com.devdock.repository;

import com.devdock.entity.Project;
import com.devdock.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserIdOrderByUpdatedAtDesc(Long userId);
    List<Project> findByUserIdAndStatusOrderByUpdatedAtDesc(Long userId, ProjectStatus status);
    Optional<Project> findByIdAndUserId(Long id, Long userId);
    long countByUserId(Long userId);
    long countByUserIdAndStatus(Long userId, ProjectStatus status);

    @Query("SELECT p FROM Project p WHERE p.user.id = :userId AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Project> searchProjects(@Param("userId") Long userId, @Param("search") String search);
}
