package com.api.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * Utility class containing static methods to generate and validate a JWTS token.
 */
public class JWTUtil {
    private static final Key key;
    /** Refresh token expires after 30 days **/
    private static final long RT_DAYS_TO_EXP = TimeUnit.DAYS.toMillis(1); // TODO - change back to whatever
    /** Auth token expires after 1 day **/
    private static final long AT_DAYS_TO_EXP = TimeUnit.HOURS.toMillis(1);

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jwt");
        key = Keys.hmacShaKeyFor(resourceBundle.getString("jwt.secret_key").getBytes());
    }

    /**
     * Private constructor
     */
    private JWTUtil(){}

    /**
     * Generates a JWTS token.
     * <br/><br/>
     * Parts:
     * - (sub) the user
     * - (isActive) weather of not an email has been activated by the user
     * - (exp) when the jwt will expire
     *
     * @param userId - the subject to generate the token for (username)
     * @param emailActive -
     * @return token - the jwts token
     */
    public static String getJwts(String userId, Boolean emailActive, Boolean rememberMe) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("emailActive", emailActive)
                .claim("rememberMe", rememberMe)
                .setExpiration(new Date(System.currentTimeMillis() +
                        (rememberMe? RT_DAYS_TO_EXP : AT_DAYS_TO_EXP)
                ))
                .signWith(key)
                .compact();
    }



    /**
     * Verifies the validity of a jwts token.
     * <br/>
     * Throws:<br/>
     * - no claims<br/>
     * - not valid jws<br/>
     * - sig validation fails<br/>
     * - expired<br/>
     * - null/empty/only whitespace<br/>
     *
     * @param token - the JWT token to verify
     * @return Claims - the claims in the jwt
     */
    public static Jws<Claims> verifyJwts(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
    }
}
