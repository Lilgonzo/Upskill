package com.api.controllers;

import com.api.entities.Message;
import com.api.filters.Secured;
import com.api.managers.ConversationManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("conversations")
public class ConversationController {
    ConversationManager conversationManager;

    public ConversationController() {
        conversationManager = new ConversationManager();
    }

    @GET
    @Secured
    @Path("{convoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findConversationMessages(@PathParam("convoId") Integer convoId) throws Exception {
        return Response.ok(conversationManager.findConversationMessages(convoId)).build();
    }

    @DELETE
    @Secured
    @Path("{convoId}")
    public Response deleteConversation(@Context SecurityContext securityContext, @PathParam("convoId") Integer convoId) throws Exception {
        conversationManager.deleteConversation(securityContext, convoId);
        return Response.ok().build();
    }

    @POST
    @Secured
    @Path("conversation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendMessage(@Context SecurityContext securityContext, Message message) throws Exception {
        return Response.ok(conversationManager.sendMessage(securityContext, message)).build();
    }

}