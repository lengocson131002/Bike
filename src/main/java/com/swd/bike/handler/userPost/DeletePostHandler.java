package com.swd.bike.handler.userPost;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.userPost.request.DeletePostRequest;
import com.swd.bike.dto.userPost.response.PostResponse;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.PostStatus;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeletePostHandler extends RequestHandler<DeletePostRequest, PostResponse> {

    private final IPostService postService;
    private final ContextService contextService;

    @Override
    @Transactional
    public PostResponse handle(DeletePostRequest request) {
        Post post = postService.getPost(request.getId());
        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        if (!postService.isAuthor(contextService.getLoggedInUser(), post))  {
            log.error("Current user is not post's author");
            throw new InternalException(ResponseCode.FAILED);
        }

        List<Account> postApplications = post.getApplications();
        if (!CollectionUtils.isEmpty(postApplications)) {
            // Todo notify to all applier
            postApplications.forEach(applier -> {
                log.info("Notify to: " + applier.getName());
            });
        }

        post.setStatus(PostStatus.COMPLETED);
        Post savedPost = postService.savePost(post);
        return new PostResponse(savedPost);
    }
}
