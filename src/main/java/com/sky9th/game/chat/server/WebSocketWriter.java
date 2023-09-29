package com.sky9th.game.chat.server;

import com.google.protobuf.Message;
import com.sky9th.game.chat.proto.Respawn;
import com.sky9th.game.chat.services.DataPool;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketWriter {

    private final DataPool dataPool;

    public byte[] write (List<Object> dataList) {
        int totalLength = 0;
        byte[] totalDataBytes = new byte[0];
        for (Object obj: dataList) {
            Message msg = (Message) obj;
            byte[] item = msg.toByteArray();
            int currentLength = item.length;

            String currentLengthStr = String.format("%08d", currentLength);
            String[] typeArray = msg.getClass().getName().split("\\.");
            StringBuilder typeStr = new StringBuilder(typeArray[typeArray.length - 1]);
            while (typeStr.length() < 12) {
                typeStr.insert(0, "0");
            }

            byte[] currentLengthBytes =  currentLengthStr.getBytes();
            byte[] typeBytes =  typeStr.toString().getBytes();
            byte[] currentDataBytes = new byte[currentLengthBytes.length + item.length + typeBytes.length];

            System.arraycopy(currentLengthBytes, 0, currentDataBytes, 0, currentLengthBytes.length);
            System.arraycopy(typeBytes, 0, currentDataBytes, currentLengthBytes.length, typeBytes.length);
            System.arraycopy(item, 0, currentDataBytes, currentLengthBytes.length + typeBytes.length, item.length);

            totalLength += currentDataBytes.length;

            byte[] currentTotalDataBytes = new byte[totalLength];
            System.arraycopy(totalDataBytes, 0, currentTotalDataBytes, 0, totalDataBytes.length);
            System.arraycopy(currentDataBytes, 0, currentTotalDataBytes, totalDataBytes.length, currentDataBytes.length);

            totalDataBytes = currentTotalDataBytes;
        }
        String totalLengthStr = String.format("%08d", totalLength);
        byte[] totalLengthBytes =  totalLengthStr.getBytes();

        byte[] returnDataBytes = new byte[totalLength + totalLengthBytes.length];

        System.arraycopy(totalLengthBytes, 0, returnDataBytes, 0, totalLengthBytes.length);
        System.arraycopy(totalDataBytes, 0, returnDataBytes, totalLengthBytes.length, totalDataBytes.length);

        if (totalLength > 0) {
            return returnDataBytes;
        }
        return null;
    }

    public byte[] getBroadcastData() {
        return getDataList(dataPool.getPlayers());
    }

    public byte[] getRespawnData () {
        log.info(String.valueOf(dataPool.getRespawns().size()));
        return getDataList(dataPool.getRespawns());
    }

    private <T, B> byte[] getDataList(Dictionary<T, B> data) {
        try {
            List<Object> dataList = new ArrayList<>();
            Enumeration<B> dataElements = data.elements();
            while (dataElements.hasMoreElements()) {
                B msg = dataElements.nextElement();
                if (msg instanceof Respawn) {
                    log.info(msg.toString());
                }
                if (msg != null) {
                    dataList.add(msg);
                }
            }
            if (dataList.size() > 0) {
                return write(dataList);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
