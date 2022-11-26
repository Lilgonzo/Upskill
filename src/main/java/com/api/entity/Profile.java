package com.api.entity;

public class Profile {
    private int userID;
    private String username;
    private String password;
    private String email;

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    private Profile(ProfileBuilder profileBuilder) {
        this.userID = profileBuilder.userID;
        this.email = profileBuilder.email;
        this.password = profileBuilder.password;
        this.username = profileBuilder.username;
    }

    public static class ProfileBuilder {
        private int userID;
        private String username;
        private String password;
        private String email;

        public ProfileBuilder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public ProfileBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public ProfileBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public ProfileBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Profile build() {
            return new Profile(this);
        }
    }
} 
