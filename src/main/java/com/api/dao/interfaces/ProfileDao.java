package com.api.dao.interfaces;

import com.api.entity.JWT;
import com.api.entity.Profile;
import jakarta.ws.rs.core.SecurityContext;

public interface ProfileDao {
    // CRUD OPERATIONS

    /**
     * Creates a new profile.
     * @param profile the object parameters passed to create a new profile
     * @throws Exception e
     */
    JWT createProfile(Profile profile) throws Exception;

    /**
     * Returns a profile given the username.
     * @param securityContext the credentials of the user provided from the JWT token
     * @param username the user to retrieve profile data for
     * @throws Exception e
     */
    Profile getProfile(SecurityContext securityContext, String username) throws Exception;

    /**
     * Updates username.
     * @param securityContext the credentials of the user provided from the JWT token
     * @param profile the object parameters passed with the username attribute to set
     * @throws Exception e
     */
    void updateUsername(SecurityContext securityContext, Profile profile) throws Exception;

    /**
     * Updates password.
     * @param securityContext the credentials of the user provided from the JWT token
     * @param profile the object parameters passed with the password attribute to set
     * @throws Exception e
     */
    void updatePassword(SecurityContext securityContext, Profile profile) throws Exception;

    /**
     * Updates bio.
     * @param securityContext the credentials of the user provided from the JWT token
     * @param profile the object parameters passed with the bio attribute to set
     * @throws Exception e
     */
    void updateBio(SecurityContext securityContext, Profile profile) throws Exception;

    /**
     * Updates email.
     * @param securityContext the credentials of the user provided from the JWT token
     * @param profile the object parameters passed with the email attribute to set
     * @throws Exception e
     */
    void updateEmail(SecurityContext securityContext, Profile profile) throws Exception;

    /**
     * Deletes profile.
     * @param securityContext the credentials of the user provided from the JWT token
     * @throws Exception e
     */
    void deleteProfile(SecurityContext securityContext) throws Exception;

    /**
     * Returns a JWT token if login was a success other throws an exception.
     * @param profile the object parameters passed with the username and password to check
     * @throws Exception e
     */
    JWT loginProfile(Profile profile) throws Exception;
}