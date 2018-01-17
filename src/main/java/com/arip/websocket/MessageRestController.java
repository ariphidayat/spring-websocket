package com.arip.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

/**
 * Created by Arip Hidayat on 1/17/2018.
 */
@RestController
@RequestMapping(value = "send")
public class MessageRestController {

    @Autowired
    private WebSocketHandler webSocketHandler;

    @GetMapping("{to}/{content}")
    public ResponseEntity test(@PathVariable("to") String to, @PathVariable("content") String content) {
        webSocketHandler.send(to, new TextMessage("SYSTEM : " + content));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("{content}")
    public ResponseEntity test(@PathVariable("content") String content) {
        webSocketHandler.sendAll(new TextMessage("SYSTEM : " + content));
        return new ResponseEntity(HttpStatus.OK);
    }
}
