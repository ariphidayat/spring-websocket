package com.arip.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by Arip Hidayat on 9/25/2017.
 */
@Controller
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void onMessage(@Payload Message msg, @DestinationVariable("to") String to, Principal principal) {
        msg.setFrom(principal.getName());
        simpMessagingTemplate.convertAndSendToUser(to,"/topic/messages", msg);
    }
}
