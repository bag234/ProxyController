package org.mrbag.ProxyController.API;

import org.mrbag.ProxyController.Repository.ProxyServerRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/ProxyServer")
public class ProxyServerController {

	ProxyServerRep repo;
	
	@Autowired
	public void setRepo(ProxyServerRep repo) {
		this.repo = repo;
	}
	
	/**
	 * list's; add new, info, and create Principal, Jbects;
	 */
	
	@PostMapping(path = "/new")
	public String makeNewAccesPointServer() {
		
	}
	
}
