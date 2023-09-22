package com.sky9th.game.chat.services;

import com.sky9th.game.chat.protos.PlayerInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.Hashtable;

@Slf4j
@Service
@Data
public class DataPool {

    public Dictionary<ChannelId, Channel> connections = new Hashtable<>();
    public Dictionary<ChannelId, PlayerInfo> players = new Hashtable<>();

    public void close(ChannelId channelId) {
        connections.remove(channelId);
        players.remove(channelId);
    }

}
