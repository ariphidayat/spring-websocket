package com.arip.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

/**
 * Created by Arip Hidayat on 1/17/2018.
 */
public interface WebSocketSessionRepository {

    void save(String username, WebSocketSession webSocketSession);

    WebSocketSession getByUsername(String username);

    void remove(WebSocketSession webSocketSession);

    void removeByUsername(String username);

    void removeAllByUsernames(List<String> username);

    Map<String, WebSocketSession> getSession();

    boolean isOnline(String username);
}
