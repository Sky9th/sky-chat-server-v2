package com.sky9th.game.chat.subscriber;

import com.sky9th.game.chat.server.WebSocketPublisher;
import com.sky9th.game.chat.server.WebSocketWriter;
import com.sky9th.game.chat.services.DataPool;
import com.sky9th.game.chat.subscriber.event.RespawnChangeEvent;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VariableChangeEventListener {

    private final WebSocketPublisher webSocketPublisher;

    private final WebSocketWriter webSocketWriter;

    @EventListener
    public void handleRespawnChangeEvent(RespawnChangeEvent event) {
        log.info("trigger event RespawnChangeEvent");
        webSocketPublisher.broadcast(webSocketWriter.getRespawnData());
    }
}