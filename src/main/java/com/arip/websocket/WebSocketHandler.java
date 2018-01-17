package com.arip.websocket;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Arip Hidayat on 1/17/2018.
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private WebSocketSessionRepository webSocketSessionRepository;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map messageMap = new Gson().fromJson(message.getPayload(), Map.class);
        String from = (String) session.getAttributes().get("username");
        String to = (String) messageMap.get("to");
        TextMessage content = new TextMessage(from + " : " + messageMap.get("content"));
        if (to.equals("public")) {
            sendAll(content);
        } else {
            send(to, content);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        webSocketSessionRepository.save(username, session);

        if (username == null && session != null && session.isOpen()) {
            session.close();
        }
        String from = (String) session.getAttributes().get("username");
        sendAll(new TextMessage("SYSTEM : " + from + " connected!"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionRepository.remove(session);
        String from = (String) session.getAttributes().get("username");
        sendAll(new TextMessage("SYSTEM : " + from + " disconnected!"));
    }

    public boolean send(String username, TextMessage message) {
        WebSocketSession webSocketSession = webSocketSessionRepository.getByUsername(username);
        if (webSocketSession == null || !webSocketSession.isOpen()) {
            return false;
        }

        return send(webSocketSession, message);
    }

    public boolean send(WebSocketSession webSocketSession, TextMessage message) {
        if (webSocketSession == null || !webSocketSession.isOpen()) {
            return false;
        }

        try {
            webSocketSession.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void sendAll(TextMessage message) {
        Iterator<Entry<String, WebSocketSession>> iterator = webSocketSessionRepository.getSession().entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, WebSocketSession> entry = iterator.next();
            WebSocketSession webSocketSession = entry.getValue();
            if (webSocketSession != null && webSocketSession.isOpen()) {
                try {
                    webSocketSession.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void close(String username) {
        WebSocketSession webSocketSession = webSocketSessionRepository.getByUsername(username);
        try {
            webSocketSession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
