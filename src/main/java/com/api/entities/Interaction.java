
package com.api.entities;

public class Interaction{
    
    int like;
    Profile toUser;
    Profile fromUser;

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Profile getToUser() {
        return toUser;
    }

    public void setToUser(Profile toUser) {
        this.toUser = toUser;
    }

    public Profile getFromUser() {
        return fromUser;
    }

    public void setFromUser(Profile fromUser) {
        this.fromUser = fromUser;
    }
}
