package com.sky9th.game.chat.server;

import com.sky9th.game.chat.protos.Message;
import com.sky9th.game.chat.protos.PlayerInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    private int acceptLength = 0;
    private int totalLength = 0;

    private byte[] completeDataBytes;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info(msg.toString());
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf buf = ((BinaryWebSocketFrame) msg).content();
            byte[] currentBytes = new byte[buf.capacity()];
            buf.readBytes(currentBytes);

            byte[] totalLengthBytes = new byte[8];
            System.arraycopy(currentBytes, 0, totalLengthBytes, 0, totalLengthBytes.length);

            totalLength = Integer.parseInt(new String(totalLengthBytes));
            log.info(String.valueOf(totalLength));
            //log.info("total length:" + String.valueOf(totalLength) + ",current length:" + String.valueOf(buf.capacity()) + ",accept length:" + acceptLength);
            completeDataBytes = new byte[totalLength];

            System.arraycopy(currentBytes, totalLengthBytes.length, completeDataBytes, acceptLength, currentBytes.length - totalLengthBytes.length);
            acceptLength += buf.capacity() - totalLengthBytes.length;
        } else if (msg instanceof ContinuationWebSocketFrame) {
            ByteBuf buf = ((ContinuationWebSocketFrame) msg).content();

            byte[] currentBytes = new byte[buf.capacity()];
            buf.readBytes(currentBytes);
            System.arraycopy(currentBytes, 0, completeDataBytes, acceptLength, currentBytes.length);
            acceptLength += buf.capacity();

            if(totalLength <= acceptLength) {
                int readTimes = 0;
                int readLength = 0;
                while (readLength < acceptLength) {
                    int currentLength = 0;
                    byte[] lengthBytes = new byte[8];
                    System.arraycopy(completeDataBytes, 0, lengthBytes, 0, lengthBytes.length);
                    currentLength += lengthBytes.length;
                    int length = Integer.parseInt(new String(lengthBytes));
                    byte[] dataBytes = new byte[length];
                    System.arraycopy(completeDataBytes, lengthBytes.length, dataBytes, 0, dataBytes.length);
                    currentLength += dataBytes.length;
                    Message message = Message.parseFrom(dataBytes);
                    switch (message.getType()) {
                        case "PlayerInfo":
                            PlayerInfo obj = PlayerInfo.parseFrom(dataBytes);
                            break;
                    }
                    readLength += currentLength;
                    readTimes ++;
                }
                log.info("receive msg from: " + ctx.channel() + " ,length:" + readLength + " ,times:" + readTimes);
                acceptLength = 0;
            }
        } else {
            throw new RuntimeException("unknown data type");
        }
    }
}