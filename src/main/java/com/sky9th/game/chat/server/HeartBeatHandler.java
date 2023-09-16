package com.sky9th.game.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if(evt instanceof IdleStateEvent event) {
            if (event.state() == IdleState.ALL_IDLE) {
                log.info("Close Client channel:" + ctx.channel() + " for 5s idle");
                ctx.close();
            }
        }
    }
}
