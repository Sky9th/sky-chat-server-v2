package com.sky9th.game.chat;

import com.sky9th.game.chat.server.WebSocketHandler;
import com.sky9th.game.chat.server.WebSocketServer;
import lombok.RequiredArgsConstructor;

public class SocketApplication {

	public static void main(String[] args) throws Exception {
		new WebSocketServer().run();
	}

}
