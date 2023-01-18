package com.swd.bike.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Using to get current log in user
 * Author: lnson
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContextService {

    @Value("${keycloak.resource}")
    private String keycloakClientId;

    private AccessToken getLoggedInAccessToken() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null ||
                    !authentication.isAuthenticated() ||
                    !(authentication instanceof KeycloakAuthenticationToken)) {
                return null;
            }
            KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
            KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) token.getPrincipal();

            AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
            return accessToken;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public Optional<String> getLoggedInUsername() {
        AccessToken loggedInToken = getLoggedInAccessToken();
        return loggedInToken != null
                ? Optional.of(loggedInToken.getPreferredUsername())
                : Optional.empty();
    }

    /**
     * Get subject ID (Keycloak UserID)
     * @return
     */
    public Optional<String> getLoginUserId() {
        AccessToken loggedInToken = getLoggedInAccessToken();
        return loggedInToken != null
                ? Optional.of(loggedInToken.getSubject())
                : Optional.empty();
    }

    public Set<String> getLoggedInUserRoles() {
        try {
            AccessToken loggedInToken = getLoggedInAccessToken();
            return loggedInToken != null
                    ? loggedInToken.getResourceAccess(keycloakClientId).getRoles()
                    : new HashSet<>();
        } catch (Exception ex) {
            log.error("Get Keycloak user roles failed, {}", ex.getMessage());
        }
        return new HashSet<>();
    }

}
