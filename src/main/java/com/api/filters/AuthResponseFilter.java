package com.api.filters;

import com.api.security.SecurityContextMapper;
import com.api.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

/**
 * Sets Auth header with updated token.
 */
@Provider
@Secured
@Priority(Priorities.HEADER_DECORATOR)
public class AuthResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
        Claims claims = SecurityContextMapper.getClaims();

        // Error with jwt auth, skip issuing new jwt (must log in)
        if (claims != null) {

            long sixDaysBeforeExpiration = claims.getExpiration().getTime() - 518400000L;
            if (sixDaysBeforeExpiration <= System.currentTimeMillis()) {
                // set AUTH header with new JWTS token
                containerResponseContext
                        .getHeaders()
                        .putSingle(
                                HttpHeaders.AUTHORIZATION,
                                JWTUtil.getJwts(claims.getSubject())
                        );
            }
        }
    }

}
