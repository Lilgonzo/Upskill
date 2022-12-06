package com.api.controllers;

import com.api.filters.Secured;
import com.api.managers.InteractionManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("interactions")
public class InteractionController {

    InteractionManager interactionManager;

    public InteractionController() {
        interactionManager = new InteractionManager();
    }

    @GET
    @Secured
    @Path("matches")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatches(@Context SecurityContext securityContext) throws Exception {
        return Response.ok(interactionManager.getMatches(securityContext)).build();
    }

    @PATCH
    @Secured
    @Path("like")
    public Response likeProfile(@Context SecurityContext securityContext) throws Exception{
        return Response.ok(interactionManager.likeUser(securityContext)).build();
    }
        
    //public Response dislikeProfile(@Context SecurityContext securityContext, )
}