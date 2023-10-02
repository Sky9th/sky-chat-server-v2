package com.sky9th.game.chat.services;

import com.sky9th.game.chat.proto.PlayerInfo;
import com.sky9th.game.chat.proto.Respawn;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
@Data
public class DataPool {

    Dictionary<ChannelId, Channel> connections = new Hashtable<>();

    Dictionary<ChannelId, PlayerInfo> players = new Hashtable<>();

    Dictionary<String, Respawn> respawns = new Hashtable<>();

    public void close(ChannelId channelId) {
        String networkId = players.get(channelId).getNetworkID();
        players.remove(channelId);
        respawns.remove(networkId);
        connections.remove(channelId);
    }

}
