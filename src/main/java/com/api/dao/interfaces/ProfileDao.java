package com.api.dao.interfaces;

import com.api.entity.JWT;
import com.api.entity.Profile;
import jakarta.ws.rs.core.SecurityContext;

public interface ProfileDao {
    // CRUD OPERATIONS
    void createProfile(Profile profile) throws Exception;
    Profile getProfile(SecurityContext securityContext, String username) throws Exception;
    void updateProfile(SecurityContext securityContext, Profile profile) throws Exception;
    void deleteProfile(SecurityContext securityContext) throws Exception;

    JWT loginProfile(Profile profile) throws Exception;
}