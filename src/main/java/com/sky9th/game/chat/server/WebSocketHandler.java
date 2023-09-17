package com.sky9th.game.chat.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
            byte[] bytes = new byte[8];
            buf = buf.readBytes(bytes);
            String type = new String(bytes);
            log.info(String.valueOf(Integer.parseInt(type)));
            String str = buf.toString(StandardCharsets.UTF_8);
            log.info("receive msg from: " + ctx.channel() + " type:" + type + ", content:" +str);
        }

    }
}