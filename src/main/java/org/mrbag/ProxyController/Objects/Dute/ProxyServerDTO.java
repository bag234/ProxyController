package org.mrbag.ProxyController.Objects.Dute;

import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.StatusServer;
import org.mrbag.ProxyController.Objects.TypeProxy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProxyServerDTO {

	String name;
	
	String ip;
	
	int port;
	
	String token;
	
	public ProxyServerDTO(long id, String name, String ip, int port, TypeProxy type, StatusServer status, String token, double cost) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.token = token;
	}
	
	public static ProxyServerDTO toDTO(ProxyServers serv) {
		return new ProxyServerDTO(0, serv.getName(), serv.getIp(), serv.getPort(), null, null, serv.getToken(), 0);
	}
	
}
