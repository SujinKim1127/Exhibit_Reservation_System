package com.ers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Likes {
    private int wish_id;
    private int user_id;
    private int exhibit_id;

    public int getWish_id() {
        return wish_id;
    }

    @JsonCreator
    public Likes(@JsonProperty("wish_id") int wish_id, @JsonProperty("user_id") int user_id, @JsonProperty("exhibit_id") int exhibit_id){
        this.wish_id = wish_id;
        this.exhibit_id = exhibit_id;
        this.user_id = user_id;
    }

    public void setWish_id(int wish_id) {
        this.wish_id = wish_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getExhibit_id() {
        return exhibit_id;
    }

    public void setExhibit_id(int exhibit_id) {
        this.exhibit_id = exhibit_id;
    }
}
