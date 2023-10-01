package com.sky9th.game.chat.server;

import com.sky9th.game.chat.services.DataPool;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketPublisher {

    private final DataPool dataPool;

    public void send (ChannelId channelId, byte[] data) {
        Channel channel = dataPool.getConnections().get(channelId);
        channel.writeAndFlush(data);
    }

    public void broadcast (byte[] data) {
        try {
            Enumeration<Channel> values = dataPool.getConnections().elements();
            if (data != null && data.length > 0) {
                while (values.hasMoreElements()) {
                    Channel value = values.nextElement();
                    value.writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(data)));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }


}
