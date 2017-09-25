package com.arip.websocket;

/**
 * Created by Arip Hidayat on 9/25/2017.
 */
public class Message {

    private String from;
    private String content;

    public Message() {}

    public Message(String from, String content) {
        this.from = from;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
