package com.sky9th.game.chat.services;

import com.sky9th.game.chat.protos.PlayerInfo;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Dictionary;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final DataPool dataPool;

    public void playerHandler (PlayerInfo playerInfo, ChannelId channelId) {
        Dictionary<ChannelId, PlayerInfo> playerPool = dataPool.getPlayers();
        playerPool.put(channelId, playerInfo);
    }

}
