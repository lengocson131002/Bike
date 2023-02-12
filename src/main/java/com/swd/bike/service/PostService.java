package com.swd.bike.service;

import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.repository.PostRepository;
import com.swd.bike.scheduler.ClearExpiredPostTask;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final TaskSchedulerService schedulerService;

    @Override
    public boolean isExistWithActiveStation(Specification<Post> specification) {
        return postRepository.exists(specification);
    }

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
        return post != null
                && PostStatus.CREATED.equals(post.getStatus())
                && post.getStartTime().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAuthor(Account author, Post post) {
        return author != null
                && post != null
                && post.getAuthor() != null
                && post.getAuthor().getId().equals(author.getId());
    }

    @Override
    public Page<Post> getAppliedPost(Account account, Pageable pageable) {
        if (account == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        return postRepository.findByApplicationsContaining(account.getId(), pageable);
    }

    @Override
    public void scheduleClearExpiredPost(Post post) {
        Instant clearTime = TimeUtils.convertLocalDateTimeToInstant(post.getStartTime());
        ClearExpiredPostTask task = new ClearExpiredPostTask(post.getId(), clearTime);
        schedulerService.schedule(task);
    }
}
