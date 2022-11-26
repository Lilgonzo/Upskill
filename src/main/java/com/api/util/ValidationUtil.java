package com.api.util;

public class ValidationUtil {

    private ValidationUtil() {};

    /**
     * todo
     * @param password
     * @return
     */
    public static boolean validatePasswordFormat(String password) {
        // todo validate password (0 < password > 31) chars long
    }

    /**
     * todo
     * @param username
     * @return
     */
    public static boolean validateUsernameFormat(String username) {
        // todo validate password (0 < username > 21) chars long
        // eg.
        return username.matches("/* SOME REGEX VALUE */");
    }

    private static boolean validateBioFormat(String bio) {
        // todo validate bio (0 < bio > 31) chars long
    }

    public static boolean validateEmailFormat(String email) {
        // todo google has good email regex validators
    }
}
