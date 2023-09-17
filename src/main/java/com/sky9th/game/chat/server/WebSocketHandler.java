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
            while (buf.isReadable()) {
                byte[] bytes = new byte[8];
                buf = buf.readBytes(bytes);
                int length = Integer.parseInt(new String(bytes));
                byte[] data = new byte[length];
                buf = buf.readBytes(data);
                String str = new String(data);
                log.info("receive msg from: " + ctx.channel() + " length:" + length + ", content:" +str);
            }
        }

    }
}