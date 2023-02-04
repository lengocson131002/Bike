package com.swd.bike.service.interfaces;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.metadata.PostgresTableMetaDataProvider;

import java.time.LocalDateTime;
import java.util.List;

public interface IPostService {
    /**
     * Filter and paging posts
     * @param spec
     * @param pageable
     * @return pageResult
     */
    Page<Post> getAllPosts(Specification<Post> spec, Pageable pageable);

    Post findAnyPostStartAt(Account account, LocalDateTime startTime, Long threshold);

    Post savePost(Post post);

    Post getPost(Long id);

    /**
     * Check if a post still online
     * @param post
     * @return
     */
    boolean isPostActive(Post post);

    boolean isAuthor(Account author, Post post);
}
