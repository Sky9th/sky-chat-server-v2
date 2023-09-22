package com.sky9th.game.chat.server;

import com.sky9th.game.chat.protos.PlayerInfo;
import com.sky9th.game.chat.protos.PlayerList;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WebSocketRunner  {

    private final WebSocketServer webSocketServer;
    private final DataPool dataPool;
    private final WebSocketWriter webSocketWriter;

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

    @Scheduled(fixedDelay = 1000)
    public void broadcast () {
        Enumeration<Channel> values = dataPool.connections.elements();
        byte[] broadcastData = webSocketWriter.getBroadcastData();
        if (broadcastData.length > 0) {
            while (values.hasMoreElements()) {
                Channel value = values.nextElement();
                value.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(broadcastData)));
                log.info("send broadcast data length:" + broadcastData.length);
            }
        }
    }
}
