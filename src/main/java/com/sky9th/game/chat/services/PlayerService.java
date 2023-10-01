package com.sky9th.game.chat.services;

import com.sky9th.game.chat.proto.PlayerInfo;
import com.sky9th.game.chat.proto.Respawn;
import com.sky9th.game.chat.proto.RespawnType;
import com.sky9th.game.chat.subscriber.event.RespawnChangeEvent;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Dictionary;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerService {

    private final ApplicationEventPublisher eventPublisher;

    private final DataPool dataPool;

    private int lastRespawns = 0;

    public void playerHandler (PlayerInfo playerInfo, ChannelId channelId) {
        Dictionary<ChannelId, PlayerInfo> playerPool = dataPool.getPlayers();
        playerPool.put(channelId, playerInfo);

        Respawn respawn = Respawn.newBuilder()
                .setNetworkID(playerInfo.getNetworkID())
                .setType("Respawn")
                .setRespawnType(RespawnType.Player)
                .build();
        dataPool.respawns.put(respawn.getNetworkID(), respawn);
        if (lastRespawns != dataPool.getRespawns().size()) {
            eventPublisher.publishEvent(new RespawnChangeEvent(this));
            lastRespawns = dataPool.getRespawns().size();
        }
    }

}
