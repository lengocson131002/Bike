package com.swd.bike.controller.interfaces;

import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.common.StatusResponse;
import com.swd.bike.dto.user.request.UpdateUserRequest;
import com.swd.bike.dto.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Tag(name = "User Controller", description = "Thao tác với users")
@RequestMapping(value = "/api/v1/users")
public interface IUserController {

    @Operation(
            summary = "Get User Info",
            description = "- Lấy thông tin user"
    )
    @GetMapping("/getInfo")
    ResponseEntity<ResponseBase<UserResponse>> getUserInfo();

    @Operation(
            summary = "Update User",
            description = "- Cập nhật thông tin user"
    )
    @PutMapping()
    ResponseEntity<ResponseBase<StatusResponse>> update(@Valid @RequestBody UpdateUserRequest request);
}
