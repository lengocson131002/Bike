package com.swd.bike.scheduler;

import com.swd.bike.config.SpringContext;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.service.interfaces.IPostService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
public class ClearExpiredPostTask extends ScheduledTask {

    private Long postId;
    private IPostService postService;

    public ClearExpiredPostTask(Long postId, Instant time) {
        super(time);
        this.postId = postId;
        postService = SpringContext.getBean(IPostService.class);
    }

    @Override
    @Transactional
    public void run() {
        try {
            Post post = postService.getPost(postId);
            if (post == null
                    || PostStatus.COMPLETED.equals(post.getStatus())
                    || post.getStartTime().isAfter(LocalDateTime.now())) {
                return;
            }

            log.info("[SCHEDULING] Clear post. ID: {}", post.getId());

            post.setStatus(PostStatus.COMPLETED);
            // Todo Notification
            postService.savePost(post);
        } catch (Exception ex) {
            log.error("Clear post with ID {} failed: {}", postId, ex.getMessage());
        }

    }
}
