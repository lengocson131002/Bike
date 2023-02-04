package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.repository.PostRepository;
import com.swd.bike.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(Specification<Post> spec, Pageable pageable) {
        if (pageable == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        return postRepository.findAll(spec, pageable);
    }

    @Override
    public Post findAnyPostStartAt(Account account, LocalDateTime startTime, Long threshold) {
        if (startTime == null || account == null) {
            return null;
        }
        threshold = threshold != null ? threshold : 0;
        LocalDateTime lowerTime = startTime.minusMinutes(threshold);
        LocalDateTime upperTime = startTime.plusMinutes(threshold);
        return postRepository
                .findFirstByAuthorIdAndStatusAndStartTimeBetween(account.getId(), PostStatus.CREATED, lowerTime, upperTime)
                .orElse(null);
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPost(Long id) {
        if (id == null) {
            return null;
        }
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isPostActive(Post post) {
        return post != null && PostStatus.CREATED.equals(post.getStatus());
    }

    @Override
    public boolean isAuthor(Account author, Post post) {
        return author != null
                && post != null
                && post.getAuthor() != null
                && post.getAuthor().getId().equals(author.getId());
    }
}
