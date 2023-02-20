package com.swd.bike.repository;

import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findFirstByAuthorIdAndStatusAndStartTimeBetween(String authorId, PostStatus status, LocalDateTime from, LocalDateTime to);

    @Query("SELECT post " +
            "FROM Post post " +
            "JOIN post.applications applier " +
            "WHERE applier IS NOT NULL AND applier.id = ?1")
    Page<Post> findByApplicationsContaining(String accountId, Pageable pageable);

    @Modifying
    @Query("UPDATE Post post " +
            "SET post.status = ?1 " +
            "WHERE post IS NOT NULL AND post.startTime < ?2")
    void setPostStatusAfterTime(PostStatus status, LocalDateTime now);
}
