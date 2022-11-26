package com.api.resources;

import com.api.dao.DaoFactory;
import com.api.dao.interfaces.ProfileDao;
import com.api.entity.Profile;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("profiles")
public class ProfileResource {
    ProfileDao profileDao;

    public ProfileResource() {
        profileDao = (ProfileDao) DaoFactory.getDao(DaoFactory.DaoType.PROFILE);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginProfile(Profile profile) throws Exception {
        return Response.ok(profileDao.loginProfile(profile)).build();
    }
}
