package com.amayadream.netty.demo.webpush;

/**
 * 消息实体
 * @author :  Amayadream
 * @date :  2017.02.08 20:02
 */
public class MessageObject {

    private String userId;
    private String message;

    public MessageObject() {

    }

    public MessageObject(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
