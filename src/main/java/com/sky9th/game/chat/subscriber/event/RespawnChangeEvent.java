package com.sky9th.game.chat.subscriber.event;

import org.springframework.context.ApplicationEvent;

public class RespawnChangeEvent extends ApplicationEvent {

    public RespawnChangeEvent(Object source) {
        super(source);
    }
}