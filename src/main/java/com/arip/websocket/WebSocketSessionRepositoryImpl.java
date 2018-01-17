package com.arip.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by Arip Hidayat on 1/17/2018.
 */
@Component
public class WebSocketSessionRepositoryImpl implements WebSocketSessionRepository {

    private Map<String, WebSocketSession> webSocketSessionMap;

    public WebSocketSessionRepositoryImpl() {
        webSocketSessionMap = new Hashtable<>();
    }

    @Override
    public void save(String username, WebSocketSession webSocketSession) {
        if (username == null || webSocketSession == null) {
            return;
        }

        webSocketSessionMap.put(username, webSocketSession);
    }

    @Override
    public WebSocketSession getByUsername(String username) {
        if (username == null) {
            return null;
        }

        return webSocketSessionMap.get(username);
    }

    @Override
    public void remove(WebSocketSession webSocketSession) {
        if (webSocketSession == null) {
            return;
        }

        Iterator<Entry<String, WebSocketSession>> iterator = webSocketSessionMap.entrySet().iterator();
        List<String> usernames = new ArrayList<>();
        while (iterator.hasNext()) {
            Entry<String, WebSocketSession> entry = iterator.next();
            if (entry.getValue().equals(webSocketSession)) {
                usernames.add(entry.getKey());
            }
        }
        removeAllByUsernames(usernames);
    }

    @Override
    public void removeByUsername(String username) {
        if (username == null) {
            return;
        }
        webSocketSessionMap.remove(username);
    }

    @Override
    public void removeAllByUsernames(List<String> usernames) {
        for (String username: usernames) {
            removeByUsername(username);
        }
    }

    @Override
    public Map<String, WebSocketSession> getSession() {
        if (webSocketSessionMap == null || webSocketSessionMap.size() <= 0) {
            return null;
        }
        return webSocketSessionMap;
    }

    @Override
    public boolean isOnline(String username) {
        WebSocketSession webSocketSession = webSocketSessionMap.get(username);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            return true;
        }
        return false;
    }
}
