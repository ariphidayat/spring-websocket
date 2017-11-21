package com.arip.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.messaging.Message;

/**
 * Created by Arip Hidayat on 11/21/2017.
 */
public class ChannelInterceptorHandler extends ChannelInterceptorAdapter {

    @Autowired
    private DefaultTokenServices tokenServices;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = SimpMessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (null != accessor.getNativeHeader("token")) {
            String accessToken = String.valueOf(accessor.getNativeHeader("token").get(0));
            Authentication user = tokenServices.loadAuthentication(accessToken).getUserAuthentication();
            SecurityContextHolder.getContext().setAuthentication(user);

            accessor.setUser(user);
        }

        return message;
    }
}
