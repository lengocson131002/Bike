package com.swd.bike.controller.interfaces;


import com.swd.bike.config.OpenAPIConfig;
import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.account.request.GetDetailRequest;
import com.swd.bike.dto.account.request.GetPageByFilterRequest;
import com.swd.bike.dto.account.response.AccountPageResponse;
import com.swd.bike.dto.account.response.GetDetailResponse;
import com.swd.bike.dto.common.PageResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/cms/v1/accounts")
@Tag(name = "[Cms] Account Controller", description = "Thao tác với tài khoản")
@SecurityRequirement(name = OpenAPIConfig.BEARER_SCHEME)
public interface ICmsAccountController {
    @GetMapping("/{id}")
    ResponseEntity<ResponseBase<GetDetailResponse>> getDetailById(
            @PathVariable("id") String id);

    @GetMapping("")
    ResponseEntity<ResponseBase<PageResponse<AccountPageResponse>>> getPageByFilter(
            @ParameterObject GetPageByFilterRequest request);
}
