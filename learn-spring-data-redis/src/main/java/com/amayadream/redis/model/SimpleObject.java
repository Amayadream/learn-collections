package com.amayadream.redis.model;

/**
 * @author :  Amayadream
 * @date :  2016.07.26 22:06
 */
public class SimpleObject {

    private String name;
    private String message;

    public SimpleObject(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public SimpleObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
