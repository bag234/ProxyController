package org.mrbag.ProxyController.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.User;
import org.mrbag.ProxyController.Objects.Dute.ProxyCreditalsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilCreditals {

	ProxyCreditalsRep creditals;
	
	UtilProxyServer servers;
	
	UtilUser users;
	
	public boolean setResumeStatus(boolean isContinue, String login, String password, String token) {
		ProxyCreditals pc = creditals.findByCustomforContinue(login, password, token);
		if (isContinue) {
			if (pc.getToDate().isAfter(LocalDateTime.now())) {
				pc.setContinue(false);
			}
			else {
				///TODO Add resume process
				return false;
			}
		}else {
			pc.setContinue(false);
		}
		creditals.save(pc);
		return true;
	}
	
	public User checkAuthUser(String login, String token) {
		User usr = users.getActiveUser(token);
		if (usr == null || !usr.getLogin().equals(login))
			return null;
		return usr;
	}
	
	public ProxyCreditals makeNewCreditals(User usr, ProxyServers server) {
		if(usr == null || server == null) return null;
		if(server.getCost() > 0) {
			if (!users.operationWriting(usr.getToken(), server.getCost()))
				return null;	
		}
		return servers.addNewUser(usr, server);
	}
	
	public Collection<ProxyCreditalsDTO> getMyCreditals(User usr){
		if (usr == null) return null;
		return creditals.findAllByUser(usr);
	}
	
	public Collection<ProxyCreditals> allUserPXS(ProxyServers pxs){
		return creditals.findAllByProxyServersFiltredByTime(pxs) ;
	}
	
	@Autowired
	public void setUsers(UtilUser users) {
		this.users = users;
	}
	
	@Autowired
	public void setCreditals(ProxyCreditalsRep creditals) {
		this.creditals = creditals;
	}
	
	@Autowired
	public void setServers(UtilProxyServer servers) {
		this.servers = servers;
	}
	
}
