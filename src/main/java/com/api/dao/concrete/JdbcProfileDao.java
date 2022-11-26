package com.api.dao.concrete;

import com.api.entity.JWT;
import com.api.entity.Profile;
import com.api.dao.interfaces.ProfileDao;
import com.api.util.DbUtil;
import jakarta.ws.rs.core.SecurityContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcProfileDao implements ProfileDao {

    @Override
    public void createProfile(Profile profile) throws Exception {

    }

    @Override
    public Profile getProfile(SecurityContext securityContext, String username) throws Exception {
        return null;
    }

    @Override
    public void updateProfile(SecurityContext securityContext, Profile profile) throws Exception {

    }

    @Override
    public void deleteProfile(SecurityContext securityContext) throws Exception {

    }

    @Override
    public JWT loginProfile(Profile profile) throws Exception {


        return null;
    }
}
