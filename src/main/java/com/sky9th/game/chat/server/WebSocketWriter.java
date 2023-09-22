package com.sky9th.game.chat.server;

import com.sky9th.game.chat.protos.PlayerInfo;
import com.sky9th.game.chat.services.DataPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketWriter {

    private final DataPool dataPool;

    public byte[] write (List<byte[]> dataList) {
        int totalLength = 0;
        byte[] totalDataBytes = new byte[0];
        for (byte[] item : dataList) {
            int currentLength = item.length;

            String currentLengthStr = String.format("%08d", currentLength);

            byte[] currentLengthBytes =  currentLengthStr.getBytes();
            byte[] currentDataBytes = new byte[currentLengthBytes.length + item.length];

            System.arraycopy(currentLengthBytes, 0, currentDataBytes, 0, currentLengthBytes.length);
            System.arraycopy(item, 0, currentDataBytes, currentLengthBytes.length, item.length);

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
        return new byte[0];
    }

    public byte[] getBroadcastData() {
        List<byte[]> dataList = new ArrayList<>();
        Enumeration<PlayerInfo> enumeration = dataPool.getPlayers().elements();

        while (enumeration.hasMoreElements()) {
            PlayerInfo playerInfo = enumeration.nextElement();
            dataList.add(playerInfo.toByteArray());
            dataList.add(playerInfo.toByteArray());
        }
        return write(dataList);
    }
}
