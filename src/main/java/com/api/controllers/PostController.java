package com.api.controllers;

import com.api.entities.Post;
import com.api.filters.Secured;
import com.api.managers.PostManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("posts")
public class PostController {
    PostManager postManager;

    public PostController() { postManager = new PostManager(); }

    @POST
    @Secured
    @Path("new")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPost(@Context SecurityContext securityContext, Post post) throws Exception {
        postManager.createPost(securityContext, post);
        return Response.ok().build();
    }

    @DELETE
    @Secured
    @Path("{postId}/delete")
    public Response deletePost(@PathParam("postId") Integer postId) throws Exception {
        postManager.deletePost(postId);
        return Response.ok().build();
    }
}
