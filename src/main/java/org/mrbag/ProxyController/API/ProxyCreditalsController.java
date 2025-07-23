package org.mrbag.ProxyController.API;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creditals/")
public class ProxyCreditalsController {

	UtilProxyServer utils;
	
	@Autowired
	public void setUtils(UtilProxyServer utils) {
		this.utils = utils;
	}
	
	// /{token| server}/new
	@PostMapping("/{token}/new")
	public ProxyCreditals createNewUser(@PathVariable("token") String token) {
		return utils.addNewCreditals(token);
	}
	
}
