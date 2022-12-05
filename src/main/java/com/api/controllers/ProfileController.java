package com.api.controllers;

import com.api.managers.ProfileManager;
import com.api.entities.Profile;
import com.api.filters.Secured;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("profiles")
public class ProfileController {
    ProfileManager manager;

    public ProfileController() {
        manager = new ProfileManager();
    }

    @PATCH
    @Path("profile/logout")
    @Secured
    public Response logout() {
        manager.logout();
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("matching-skills")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSimilarSkillProfiles(@Context SecurityContext securityContext) throws Exception {
        return Response.ok(manager.getProfilesWithSimilarSkills(securityContext)).build();
    }

    @GET
    @Path("profile/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginProfile(Profile profile) throws Exception {
        return Response.ok(manager.loginProfile(profile)).build();
    }

    @DELETE
    @Secured
    @Path("/profile")
    public Response deleteProfile(@Context SecurityContext securityContext) throws Exception {
        manager.deleteProfile(securityContext);
        return Response.ok().build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProfile(Profile profile) throws Exception {
        return Response.ok(manager.createProfile(profile)).build();
    }

    @GET
    @Secured
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile(@PathParam("username") String username) throws Exception {
        return Response.ok(manager.getProfile(username)).build();
    }

    @PATCH
    @Secured
    @Path("profile/username")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsername(@Context SecurityContext securityContext, Profile profile) throws Exception {
        manager.updateUsername(securityContext, profile);
        return Response.ok().build();
    }

    @PATCH
    @Secured
    @Path("profile/bio")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBio(@Context SecurityContext securityContext, Profile profile) throws Exception {
        manager.updateBio(securityContext, profile);
        return Response.ok().build();
    }

    @PATCH
    @Secured
    @Path("profile/email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmail(@Context SecurityContext securityContext, Profile profile) throws Exception {
        manager.updateEmail(securityContext, profile);
        return Response.ok().build();
    }

    @PATCH
    @Secured
    @Path("profile/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(@Context SecurityContext securityContext, Profile profile) throws Exception {
        manager.updatePassword(securityContext, profile);
        return Response.ok().build();
    }
    
    
    //todo: add like and dislike profile 
    
    //
    //public Response likeProfile(@Context SecurityContext securityContext, )
        
    //public Response dislikeProfile(@Context SecurityContext securityContext, )
    
}
