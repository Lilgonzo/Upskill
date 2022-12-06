package com.api.controllers;

import com.api.entities.Settings;
import com.api.filters.Secured;
import com.api.managers.SettingsManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("settings")
public class SettingsController {

    SettingsManager settingsManager;

    public SettingsController() {
        settingsManager = new SettingsManager();
    }

    @PATCH
    @Secured
    @Path("setting")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSettings(@Context SecurityContext securityContext, Settings settings) throws Exception {
        settingsManager.updateSettings(securityContext, settings);
        return Response.ok().build();
    }
}
