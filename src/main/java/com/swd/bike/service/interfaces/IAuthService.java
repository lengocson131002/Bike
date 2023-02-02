package com.swd.bike.service.interfaces;

import com.swd.bike.dto.auth.response.AccessTokenResponseCustom;
import org.springframework.stereotype.Service;

public interface IAuthService {

    AccessTokenResponseCustom loginByUsernameAndPassword(String username, String password);


    AccessTokenResponseCustom loginByGoogle(String code);

    void logout(String refreshToken);

    AccessTokenResponseCustom refreshToken(String refreshToken);
}
