package org.mrbag.ProxyController.WShell;

import org.mrbag.ProxyController.Utils.UtilParseToken;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ServerEndPoint extends TextWebSocketHandler {
	
	
	private static String getToken(final WebSocketSession session) {
		return UtilParseToken.getToken(session.getUri().getQuery());
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		super.afterConnectionEstablished(session);
	}

}
