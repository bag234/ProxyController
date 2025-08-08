package org.mrbag.ProxyController.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.StatusServer;
import org.mrbag.ProxyController.Objects.User;
import org.mrbag.ProxyController.Objects.Events.EventControl;
import org.mrbag.ProxyController.Objects.Events.Imp.RepeatComand;
import org.mrbag.ProxyController.Objects.Events.Imp.UserEvent;
import org.mrbag.ProxyController.Utils.UtilCreditals;
import org.mrbag.ProxyController.Utils.UtilParseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilProxyServer {

	ProxyServerRep servers;
	
	EventControl controler;
	
	ProxyCreditalsRep creditals;
	
	UtilUser utils;
	
	@Transactional
	public void dropServer(String token) {
		servers.dropOneByToken(token);
		controler.mountLazyEvent(() -> token, RepeatComand.New("~block"));
	}
	
	public String newServer() {
		String nt = UtilParseToken.generateToken();
		while(servers.existsByToken(nt))
			nt = UtilParseToken.generateToken();
		
		ProxyServers nserv = ProxyServers.builder().token(nt).build();
		servers.save(nserv);
		
		return nt;
	}
	
	public ProxyServers getServerOrNull(String token) {
		if(token == null)
			return null;
		return servers.findOneByToken(token);
	}
	
	public static boolean canNew(ProxyServers serv) {
		if (serv.getName() == null || serv.getIp() == null || serv.getStatus() == StatusServer.NONE)
			return true;
		return false;
	}
	
	@Transactional
	public void setStatus(String token, StatusServer status) {
		servers.updateStatusByToken(token, status);
	}
	
	@Transactional
//	TODO add call after all intilazeing
	public void setAllOfline() {
		servers.updateStatusAll(StatusServer.OFFLINE);
	}
	
	public void update(ProxyServers pxs) {
		servers.saveAndFlush(pxs);
	}
	
	public Collection<ProxyServers> getAllServers(){
		return servers.findAll();
	}
	
	public Collection<ProxyCreditals> getAllActiveUser(ProxyServers pxs){
		return creditals.findAllByProxyServersFiltredByTime(pxs);
	}
	
	// TODO add check unicule user creditals on server
	public ProxyCreditals addNewUser(User usr, ProxyServers pxs) {
		ProxyCreditals pc = UtilCreditals.newCridetals(LocalDateTime.now().plusDays(1));
		pc.setPs(pxs);
		pc.setUser(usr);
		
		creditals.save(pc);
		
		controler.mountEventExcute(pxs, new UserEvent(pc));
		return pc;
	}
	
	/***
	 * Generate New User not check creditals on server
	 * @param token - access token for @see {@link ProxyServers}
	 * @return - new creditals for user;
	 */
	@Deprecated
	public ProxyCreditals addNewCreditals(String token) {
		ProxyServers pxs = getServerOrNull(token);
		if (pxs == null)
			return null;
		return null;
//		return addNewUser(pxs);
	}
	
	@Autowired
	public void setUtils(UtilUser utils) {
		this.utils = utils;
	}
	
	@Autowired
	public void setControler(EventControl controler) {
		this.controler = controler;
	}
	
	@Autowired
	public void setCreditals(ProxyCreditalsRep creditals) {
		this.creditals = creditals;
	}
	
	@Autowired
	public void setServers(ProxyServerRep servers) {
		this.servers = servers;
	}
	
}
