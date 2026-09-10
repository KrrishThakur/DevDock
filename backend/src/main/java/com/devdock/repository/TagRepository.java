package com.devdock.repository;

import com.devdock.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameIgnoreCase(String name);

    @Query("SELECT DISTINCT t FROM Tag t JOIN t.resources r WHERE r.user.id = :userId ORDER BY t.name ASC")
    List<Tag> findTagsByUserId(@Param("userId") Long userId);
}
