package org.mrbag.ProxyController.Objects.Dute;

import java.time.LocalDateTime;

import org.mrbag.ProxyController.Objects.ProxyServers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProxyCreditalsDTO {

	String login;
	
	String password;
	
	LocalDateTime toDate;
	
	//make another name 
	ProxyServerDTO ps;
	
	boolean isContinue;

	public ProxyCreditalsDTO(String login, String password, LocalDateTime toDate, ProxyServers ps, boolean isContinue) {
		this.login = login;
		this.password = password;
		this.toDate = toDate;
		this.ps = ProxyServerDTO.toDTO(ps);
		this.isContinue = isContinue;
	}
	
	
	
}
