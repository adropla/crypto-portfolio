package com.cryptolisting.springreactjs.models;

public class IdNameRequest {
    private Integer id;
    private String name;

    public IdNameRequest() {
    }

    public IdNameRequest(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
