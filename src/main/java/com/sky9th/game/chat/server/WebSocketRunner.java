package com.sky9th.game.chat.server;

import com.sky9th.game.chat.services.DataPool;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Slf4j
@Service
@AllArgsConstructor
public class WebSocketRunner  {

    private final WebSocketServer webSocketServer;
    private final WebSocketWriter webSocketWriter;

    private WebSocketPublisher webSocketPublisher;

    private final static int rate = 30;

    @Async
    @Bean
    public void webSocketInit() {
        try {
            log.info("Run netty socket server");
            webSocketServer.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedDelay = rate)
    public void broadcast () {
        byte[] broadcastData = webSocketWriter.getBroadcastData();
        webSocketPublisher.broadcast(broadcastData);
    }
}
