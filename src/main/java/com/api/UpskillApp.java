package com.api;

import com.api.controllers.ProfileController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class UpskillApp extends Application {

    public Set<Class<?>> getC() {
        final Set<Class<?>> c = new HashSet<>();
        // Resources
        c.add(ProfileController.class);

        return c;
    }
}
