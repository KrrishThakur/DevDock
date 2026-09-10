package com.devdock.repository;

import com.devdock.entity.Priority;
import com.devdock.entity.Task;
import com.devdock.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectIdOrderByCreatedAtDesc(Long projectId);
    List<Task> findByProjectUserIdOrderByDueDateAscCreatedAtDesc(Long userId);
    List<Task> findByProjectUserIdAndStatus(Long userId, TaskStatus status);
    Optional<Task> findByIdAndProjectUserId(Long id, Long userId);

    long countByProjectUserId(Long userId);
    long countByProjectUserIdAndStatus(Long userId, TaskStatus status);
    long countByProjectId(Long projectId);
    long countByProjectIdAndStatus(Long projectId, TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.project.user.id = :userId " +
           "AND (:projectId IS NULL OR t.project.id = :projectId) " +
           "AND (:status IS NULL OR t.status = :status) " +
           "AND (:priority IS NULL OR t.priority = :priority) " +
           "AND (:search IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY t.dueDate ASC NULLS LAST, t.createdAt DESC")
    List<Task> filterTasks(
        @Param("userId") Long userId,
        @Param("projectId") Long projectId,
        @Param("status") TaskStatus status,
        @Param("priority") Priority priority,
        @Param("search") String search
    );
}
