package com.ers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Mypage {
    private List<Likes> Likes;
    private List<Orders> Orders;

    @JsonCreator
    public Mypage(@JsonProperty("likes") List<Likes> Likes, @JsonProperty("orders") List<Orders> Orders){
        this.Likes = Likes;
        this.Orders = Orders;
    }

    public List<com.ers.model.Likes> getLikes() {
        return Likes;
    }

    public void setLikes(List<com.ers.model.Likes> likes) {
        Likes = likes;
    }

    public List<com.ers.model.Orders> getOrders() {
        return Orders;
    }

    public void setOrders(List<com.ers.model.Orders> orders) {
        Orders = orders;
    }
}
