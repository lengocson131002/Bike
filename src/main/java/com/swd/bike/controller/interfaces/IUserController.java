package com.swd.bike.controller.interfaces;

import com.swd.bike.core.ResponseBase;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User Controller", description = "Thao tác với users")
@RequestMapping(value = "/api/v1/users")
public interface IUserController {

    @Operation(
            summary = "Get User Info",
            description = "- Lấy thông tin user"
    )
    @GetMapping("/getInfo")
    ResponseEntity<ResponseBase<AccessTokenResponseCustom>> getUserInfo();
}
