package com.api.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("test")
public class TestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest() {
        return Response.ok().build();
    }
}
