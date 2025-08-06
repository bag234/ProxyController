package org.mrbag.ProxyController.API;


import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.Events.EventControl;
import org.mrbag.ProxyController.Objects.Events.IEvent;
import org.mrbag.ProxyController.Objects.Events.Imp.RepeatComand;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ProxyServer")
public class ProxyServerController {

	UtilProxyServer utils;
	
	EventControl control;
	
	@Autowired
	public void setControl(EventControl control) {
		this.control = control;
	}
	
	@Autowired
	public void setUtils(UtilProxyServer utils) {
		this.utils = utils;
	}

	@PostMapping(path = "/new")
	public String makeNewAccesNode() {
		System.out.println("test");
		return utils.newServer();
	}
	
	@GetMapping(path = "/all")
	public Collection<ProxyServers> listAllNodes() {
		return utils.getAllServers();	
	}

	@DeleteMapping(path = "/{token}")
	public void removeNode(@PathVariable(name = "token") String token) {
		if (token == null || token.isEmpty()) 
			return;
		utils.dropServer(token);
	}
	
	
	/*
	 * Event's control block
	 */
	
	@GetMapping(path = "{token}/events")
	public Collection<IEvent> listEventsServer(@PathVariable(name = "token") String token){
		if (token.isEmpty())
			return null;
		return control.getAllMountEvents(token);
	}
		
	@DeleteMapping(path = "{token}/events")
	public void cleanEvents(@PathVariable(name = "token") String token) {
		control.dropEvents(token);
	}
	
	/***
	 *@return boolean is complecse result sender command, if command mount handler event's or if contain c
	 */
	@PostMapping(path = "{token}/events")
	public boolean SendingCommand(
			@PathVariable(name = "token") String token,
			@RequestBody(required = false) String body) {
		if (token.isEmpty() && (body == null || body.isEmpty())) 
			return false;
		
		control.mountEventExcute((() -> token), new RepeatComand(body));
		
		return control.handlerIsMount(token);
	}
	
	
	
}
