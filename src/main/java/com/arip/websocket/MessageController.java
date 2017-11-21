package com.arip.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by Arip Hidayat on 9/25/2017.
 */
@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/")
    public String hello() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/api")
    public String api() {
        return "{\"msg\": \"Hello\"}";
    }

    @MessageMapping("/chat/{to}")
    public void onMessage(@Payload Message msg, @DestinationVariable("to") String to, Principal principal) {
        if (msg.getContent().length() < 1) {
            throw new IllegalArgumentException("Content shouldn't be empty.");
        }

        msg.setFrom(principal.getName());
        if (to.equals("public")) {
            simpMessagingTemplate.convertAndSend("/topic/messages", msg);
        }else{
            simpMessagingTemplate.convertAndSendToUser(to, "/queue/messages", msg);
        }
    }

    @MessageExceptionHandler
    @SendToUser(value = "/queue/messages", broadcast = false)
    public Message handleException(IllegalArgumentException e) {
        return new Message("ADMIN", e.getMessage());
    }
}
