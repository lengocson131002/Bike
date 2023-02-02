package com.swd.bike.service.interfaces;

import com.swd.bike.dto.auth.response.GoogleAccessTokenResponse;
import org.springframework.stereotype.Service;

public interface IGoogleService {

    GoogleAccessTokenResponse getAccessToken(String code);

    boolean isValidEmail(String domain);
}
