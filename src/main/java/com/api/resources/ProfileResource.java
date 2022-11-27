package com.api.resources;

import com.api.dao.DaoFactory;
import com.api.dao.interfaces.ProfileDao;
import com.api.entity.Profile;
import com.api.filters.Secured;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("profiles")
public class ProfileResource {
    ProfileDao profileDao;

    public ProfileResource() {
        profileDao = (ProfileDao) DaoFactory.getDao(DaoFactory.DaoType.PROFILE);
    }

    @GET
    @Path("profile/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginProfile(Profile profile) throws Exception {
        return Response.ok(profileDao.loginProfile(profile)).build();
    }

    @DELETE
    @Secured
    @Path("/profile")
    public Response deleteProfile(@Context SecurityContext securityContext) throws Exception {
        profileDao.deleteProfile(securityContext);
        return Response.ok().build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProfile(Profile profile) throws Exception {
        return Response.ok(profileDao.createProfile(profile)).build();
    }

    @GET
    @Secured
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(@Context SecurityContext securityContext, @PathParam("username") String username) throws Exception {
        return Response.ok(profileDao.getProfile(securityContext, username)).build();
    }

    @PATCH
    @Secured
    @Path("profile/username")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsername(@Context SecurityContext securityContext, Profile profile) throws Exception {
        profileDao.updateUsername(securityContext, profile);
        return Response.ok().build();
    }

    @PATCH
    @Secured
    @Path("profile/bio")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBio(@Context SecurityContext securityContext, Profile profile) throws Exception {
        profileDao.updateBio(securityContext, profile);
        return Response.ok().build();
    }

    @PATCH
    @Secured
    @Path("profile/email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmail(@Context SecurityContext securityContext, Profile profile) throws Exception {
        profileDao.updateEmail(securityContext, profile);
        return Response.ok().build();
    }

    @PATCH
    @Secured
    @Path("profile/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(@Context SecurityContext securityContext, Profile profile) throws Exception {
        profileDao.updatePassword(securityContext, profile);
        return Response.ok().build();
    }

}
