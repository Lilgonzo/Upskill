package com.api.controllers;

import com.api.entities.Interaction;
import com.api.filters.Secured;
import com.api.managers.InteractionManager;
import jakarta.ws.rs.*;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response likeProfile(@Context SecurityContext securityContext, Interaction interaction) throws Exception {
        return Response.ok(interactionManager.likeUser(securityContext, interaction)).build();
    }
}