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
    private final DataPool dataPool;
    private final WebSocketWriter webSocketWriter;

    private final static int rate = 1000;

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
        try {
            boolean broadcastRespawn = false;
            byte[] respawnData = null;
            if (!dataPool.getInits().isEmpty()) {
                dataPool.getInits().remove();
                respawnData = webSocketWriter.getRespawnData();
                if (respawnData != null && respawnData.length > 0) {
                    broadcastRespawn = true;
                }
            }

            Enumeration<Channel> values = dataPool.getConnections().elements();
            byte[] broadcastData = webSocketWriter.getBroadcastData();
            if (broadcastData != null && broadcastData.length > 0) {
                while (values.hasMoreElements()) {
                    Channel value = values.nextElement();
                    value.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(broadcastData)));
                    //log.info("send broadcast data length:" + broadcastData.length);
                    if (broadcastRespawn) {
                        value.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(respawnData)));
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
