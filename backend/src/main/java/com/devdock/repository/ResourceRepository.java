package com.devdock.repository;

import com.devdock.entity.Resource;
import com.devdock.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Resource> findByIdAndUserId(Long id, Long userId);
    List<Resource> findByTaskIdOrderByCreatedAtDesc(Long taskId);
    long countByUserId(Long userId);
    long countByUserIdAndType(Long userId, ResourceType type);

    @Query("SELECT DISTINCT r FROM Resource r LEFT JOIN r.tags t WHERE r.user.id = :userId " +
           "AND (:type IS NULL OR r.type = :type) " +
           "AND (:tagName IS NULL OR LOWER(t.name) = LOWER(:tagName)) " +
           "AND (:taskId IS NULL OR r.task.id = :taskId) " +
           "AND (:search IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(r.url) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(r.notes) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "ORDER BY r.createdAt DESC")
    List<Resource> filterResources(
        @Param("userId") Long userId,
        @Param("type") ResourceType type,
        @Param("tagName") String tagName,
        @Param("taskId") Long taskId,
        @Param("search") String search
    );
}
