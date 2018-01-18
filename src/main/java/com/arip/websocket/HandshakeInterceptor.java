package com.arip.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by Arip Hidayat on 1/17/2018.
 */
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        Principal principal = ((ServletServerHttpRequest) request).getServletRequest().getUserPrincipal();
        if (principal != null) {
            attributes.put("username", principal.getName());
        } else {
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
            Authentication authentication = new UsernamePasswordAuthenticationToken("ANONYMOUS", "ANONYMOUS", authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            attributes.put("username", authentication.getName());
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
