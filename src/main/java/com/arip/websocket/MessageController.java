package com.arip.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Arip Hidayat on 9/25/2017.
 */
@Controller
public class MessageController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message onMessage(Message msg) {
        return new Message(msg.getFrom(), msg.getContent());
    }
}
