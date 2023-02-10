package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.IPostApplicationController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.application.request.GetAllApplicationsRequest;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.userPost.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostApplicationController extends BaseController implements IPostApplicationController {

    @Override
    public ResponseEntity<ResponseBase<PageResponse<PostResponse>>> getAllApplyingPost(GetAllApplicationsRequest request) {
        return this.execute(request);
    }
}
