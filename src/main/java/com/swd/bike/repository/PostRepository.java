package com.swd.bike.repository;

import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findFirstByAuthorIdAndStatusAndStartTimeBetween(String authorId, PostStatus status, LocalDateTime from, LocalDateTime to);
}
