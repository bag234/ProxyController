package org.mrbag.ProxyController.API;


import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.Events.EventControl;
import org.mrbag.ProxyController.Objects.Events.Imp.RepeatComand;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	/**
	 * list's; add new, info, and create Principal, Jbects;
	 */
	
	@GetMapping(path = "/t")
	public String test() {
		ProxyServers servs = utils.getServerOrNull("4b3c24f9fdca46bc8a0b94b3a747045e");
		control.mountEventExcute(servs, new RepeatComand("~reapiat"));
		return "test";
	}
	
	@GetMapping(path = "/t2")
	public String test2() {
		utils.setAllOfline();
		return "test";
	}
	
	@PostMapping(path = "/new")
	public String makeNewAccesPointServer() {
		System.out.println("test");
		return utils.newServer();
	}
	
}
