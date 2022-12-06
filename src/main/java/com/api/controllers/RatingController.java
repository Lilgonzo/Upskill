package com.api.controllers;

import com.api.entities.Rating;
import com.api.filters.Secured;
import com.api.managers.RatingManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("ratings")
public class RatingController {

    RatingManager ratingManager;

    public RatingController() {
        ratingManager = new RatingManager();
    }

    @PATCH
    @Secured
    @Path("rating")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rateUser(@Context SecurityContext securityContext, Rating rating) throws Exception {
        ratingManager.updateRating(securityContext, rating);
        return Response.ok().build();
    }
}