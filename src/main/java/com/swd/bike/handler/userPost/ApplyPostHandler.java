package com.swd.bike.handler.userPost;

import com.swd.bike.common.BaseConstant;
import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.userPost.request.ApplyPostRequest;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Post;
import com.swd.bike.entity.Trip;
import com.swd.bike.enums.ResponseCode;
import com.swd.bike.exception.InternalException;
import com.swd.bike.service.ContextService;
import com.swd.bike.service.interfaces.IPostService;
import com.swd.bike.service.interfaces.ITripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApplyPostHandler extends RequestHandler<ApplyPostRequest, StatusResponse> {

    private final IPostService postService;
    private final ITripService tripService;
    private final ContextService contextService;

    @Override
    @Transactional
    public StatusResponse handle(ApplyPostRequest request) {

        Account currentUser = contextService.getLoggedInUser();

        Post post = postService.getPost(request.getId());

        if (!postService.isPostActive(post)) {
            throw new InternalException(ResponseCode.POST_ERROR_NOT_FOUND);
        }
        if (Objects.equals(post.getAuthor().getId(), currentUser.getId())) {
            throw new InternalException(ResponseCode.POST_ERROR_SELF_APPLY);
        }

        Trip inProgressTrip = tripService.getCurrentTrip(currentUser);
        if (inProgressTrip != null) {
            throw new InternalException(ResponseCode.TRIP_ERROR_ON_GOING_TRIP);
        }

        Post existedPost = postService.findAnyPostStartAt(currentUser, post.getStartTime(), BaseConstant.POST_THRESHOLD_IN_MINUTES);
        if (existedPost != null) {
            throw new InternalException(ResponseCode.POST_ERROR_CONFLICT_TIME);
        }

        // Apply
        if (post.getApplications() == null) {
            post.setApplications(new ArrayList<>());
        }
        if (post.getApplications()
                .stream()
                .anyMatch(applier -> Objects.equals(applier.getId(), currentUser.getId()))) {
            throw new InternalException(ResponseCode.POST_ERROR_EXISTED_APPLIER);
        }

        post.getApplications().add(currentUser);
        Post savedPost = postService.savePost(post);

        // Todo notify to poster

        return new StatusResponse(savedPost != null);
    }
}
