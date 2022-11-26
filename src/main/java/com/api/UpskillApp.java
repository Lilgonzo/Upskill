package com.api;

import com.api.resources.ProfileResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class UpskillApp extends Application {

    public Set<Class<?>> getC() {
        final Set<Class<?>> c = new HashSet<>();
        // Resources
        c.add(ProfileResource.class);

        return c;
    }
}
