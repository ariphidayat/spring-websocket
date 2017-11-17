package com.arip.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * Created by Arip Hidayat on 11/17/2017.
 */
@Component
public class SessionConnectEventHandler implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void onApplicationEvent(SessionConnectEvent e) {
        simpMessagingTemplate.convertAndSend("/topic/messages", new Message("system", e.getUser().getName() + " connected!"));
    }
}
