package org.mrbag.ProxyController.WShell;

import java.util.HashMap;
import java.util.Map;

import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.StatusServer;
import org.mrbag.ProxyController.Objects.Events.EventControl;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.mrbag.ProxyController.Utils.UtilParseToken;
import org.mrbag.ProxyController.WShell.Command.CommandDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ServerEndPoint extends TextWebSocketHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ServerEndPoint.class);
	
	UtilProxyServer utils;
	
	Map<String, WebSocketRegulator> ss;
	
	CommandDispatcher dispatcher;
	
	EventControl control;
	
	public ServerEndPoint(UtilProxyServer utils, CommandDispatcher dispatcher, EventControl control) {
		ss = new HashMap<String, WebSocketRegulator>();
		this.dispatcher = dispatcher;
		this.utils = utils;
		this.control = control;
	}
	
	private static String getToken(final WebSocketSession session) {
		return UtilParseToken.getToken(session.getUri().getQuery());
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		ProxyServers serv = utils.getServerOrNull(getToken(session));
		if (serv == null) {
			session.close();
			return;
		}
		WebSocketRegulator wsr = new WebSocketRegulator(serv, session);
		if(wsr.isSendInfo())
			wsr.sendMessage("~info");
		ss.put(session.getId(), wsr);
		control.mountHandler(wsr, wsr);
		utils.setStatus(getToken(session), StatusServer.ONLINE);
		super.afterConnectionEstablished(session);
	}
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("{DEBUG} " + message);
		
		if (message.getPayload() instanceof String) {
			dispatcher.processCommand((String)message.getPayload(), ss.get(session.getId()));
		}
		else 
			log.warn("Wrong session data!:" + message.getPayload());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		utils.setStatus(getToken(session), StatusServer.OFFLINE);
		control.unMountHandler(ss.get(session.getId()));

		super.afterConnectionClosed(session, status);
	}

}
