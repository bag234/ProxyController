package org.mrbag.ProxyController.WShell;

import java.io.IOException;
import java.util.Collection;

import org.json.JSONObject;
import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.Events.IEventSocket;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketRegulator implements IEventSocket {
	
	ProxyServers server;
	
	WebSocketSession sesion;
	
	private static UtilProxyServer utils = null;
	
	public static void setUtils(UtilProxyServer utils) {
		WebSocketRegulator.utils = utils;
	}
	
	public boolean isValid() {
		return server != null && sesion != null && sesion.isOpen();
	}
	
	public boolean isSendInfo() {
		return UtilProxyServer.canNew(server);
	}
	
	public void sendMessage(String data) throws IOException {
		sesion.sendMessage(new TextMessage(data.getBytes()));
	}
	
	public void updateInfo() {
		if(utils != null)
			utils.update(server);
	}
	
	public void updateInfo(JSONObject obj) {
		server.setFromJson(obj);
		
		updateInfo();
	}

	@Override
	public String getEventAccesKey() {
		return server.getEventAccesKey();
	}
	
	public Collection<ProxyCreditals> getActiveUser(){
		return utils.getAllActiveUser(server);
	}
}
