package com.api.entities;

public class Rating{
    Profile fromUser;
    Profile toUser;
    int rating;

    public Profile getFromUser() {
        return fromUser;
    }

    public void setFromUser(Profile fromUser) {
        this.fromUser = fromUser;
    }

    public Profile getToUser() {
        return toUser;
    }

    public void setToUser(Profile toUser) {
        this.toUser = toUser;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
