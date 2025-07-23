package org.mrbag.ProxyController.WShell;

import org.mrbag.ProxyController.Objects.Events.EventControl;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.mrbag.ProxyController.WShell.Command.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class ConfgWS implements WebSocketConfigurer{

	UtilProxyServer utils;
	
	CommandDispatcher dispatcher;
	
	EventControl control;
	
	@Autowired
	public void setControl(EventControl control) {
		this.control = control;
	}
	
	@Autowired
	public void setDispatcher(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	@Autowired
	public void setUtils(UtilProxyServer utils) {
		this.utils = utils;
	}
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		WebSocketRegulator.setUtils(utils);
		System.out.println(control);
		registry.addHandler(new ServerEndPoint(utils, dispatcher, control), "/ws");
	}

}
