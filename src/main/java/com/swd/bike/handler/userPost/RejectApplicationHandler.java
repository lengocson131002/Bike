package com.swd.bike.handler.userPost;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userPost.request.RejectApplicationRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.AccountService;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RejectApplicationHandler extends RequestHandler<RejectApplicationRequest, StatusResponse> {

    private final IPostService postService;
    private final AccountService accountService;
    private final ContextService contextService;

    @Override
    public StatusResponse handle(RejectApplicationRequest request) {
        Post post = postService.getPost(request.getPostId());
        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }

        Account current = contextService.getLoggedInUser();
        if (!postService.isAuthor(current, post)) {
            log.error("Current user is not post's author");
            throw new InternalException(ResponseCode.FAILED);
        }

        Account rejectedApplier = accountService.findAccount(request.getApplierId());
        List<Account> applications = post.getApplications();
        if (applications == null) {
            applications = new ArrayList<>();
        }

        if (rejectedApplier == null || applications.stream().noneMatch(applier -> Objects.equals(applier.getId(), rejectedApplier.getId()))) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_EXISTED_APPLIER);
        }

        // Todo notify to rejected applier

        applications = applications.stream()
                .filter(applier -> !Objects.equals(applier.getId(), rejectedApplier.getId()))
                .collect(Collectors.toList());

        post.setApplications(applications);
        Post savedPost = postService.savePost(post);

        return new StatusResponse(savedPost != null);
    }
}
