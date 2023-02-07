package com.swd.bike.controller;

import com.swd.bike.controller.interfaces.ICmsAccountController;
import com.swd.bike.core.BaseController;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.PageResponse;
import com.swd.bike.dto.account.response.GetDetailResponse;
import com.swd.bike.dto.account.response.AccountPageResponse;
import com.swd.bike.dto.account.request.GetDetailRequest;
import com.swd.bike.dto.account.request.GetPageByFilterRequest;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AccountController extends BaseController implements ICmsAccountController {
    @Override
    public ResponseEntity<ResponseBase<GetDetailResponse>> getDetailById(
            @PathVariable String id) {
        GetDetailRequest request = new GetDetailRequest();
        request.setId(id);
        return this.execute(request);
    }

    @Override
    public ResponseEntity<ResponseBase<PageResponse<AccountPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request) {
        return this.execute(request);
    }
}
