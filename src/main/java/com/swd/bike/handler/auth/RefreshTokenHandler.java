package com.swd.bike.handler.auth;

import com.swd.bike.core.RequestHandler;
import com.swd.bike.dto.auth.request.LogoutRequest;
import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import com.swd.bike.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenHandler extends RequestHandler<LogoutRequest, AccessTokenResponseCustom> {
    private final IAuthService authService;

    @Override
    public AccessTokenResponseCustom handle(LogoutRequest request) {
        return authService.refreshToken(request.getRefreshToken());
    }
}
