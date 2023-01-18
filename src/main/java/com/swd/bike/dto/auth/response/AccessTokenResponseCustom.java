package com.swd.bike.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swd.bike.core.BaseResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.keycloak.representations.AccessTokenResponse;


import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponseCustom extends BaseResponseData {
    protected String token;

    protected long expiresIn;

    protected long refreshExpiresIn;

    protected String refreshToken;

    protected String tokenType;

    protected String idToken;

    protected int notBeforePolicy;

    protected String sessionState;

    protected Map<String, Object> otherClaims = new HashMap<>();

    @JsonProperty("scope")
    protected String scope;

    public AccessTokenResponseCustom() {

    }

    public AccessTokenResponseCustom(AccessTokenResponse accessTokenResponse) {
        this.token = accessTokenResponse.getToken();
        this.idToken = accessTokenResponse.getIdToken();
        this.expiresIn = accessTokenResponse.getExpiresIn();
        this.refreshToken = accessTokenResponse.getRefreshToken();
        this.tokenType = accessTokenResponse.getTokenType();
        this.notBeforePolicy = accessTokenResponse.getNotBeforePolicy();
        this.sessionState = accessTokenResponse.getSessionState();
        this.otherClaims = accessTokenResponse.getOtherClaims();
        this.scope = accessTokenResponse.getScope();
        this.refreshExpiresIn = accessTokenResponse.getRefreshExpiresIn();
    }
}
