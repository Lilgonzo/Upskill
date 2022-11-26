package com.api.security;

import io.jsonwebtoken.Claims;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

public class SecurityContextMapper implements SecurityContext {
    private static Claims claims;

    public static Claims getClaims() {
        return claims;
    }

    public static void setClaims(Claims claims) {
        SecurityContextMapper.claims = claims;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> claims.getSubject();
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
