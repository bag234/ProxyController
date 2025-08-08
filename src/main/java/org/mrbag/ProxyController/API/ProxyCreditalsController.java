package org.mrbag.ProxyController.API;

import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.Dute.ProxyCreditalsDTO;
import org.mrbag.ProxyController.Repository.UtilCreditals;
import org.mrbag.ProxyController.Repository.UtilProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/creditals/")
public class ProxyCreditalsController {

	@Autowired
	UtilProxyServer servers;
	
	@Autowired
	UtilCreditals creditals;
	
	// /{token| server}/new
	@PostMapping("/{token}/new")
	public ProxyCreditals createNewUser(
			@PathVariable("token") String token,
			@RequestBody UserDTO dto
			) {
		return creditals.makeNewCreditals(
				creditals.checkAuthUser(dto.getLogin(), dto.getToken()),
				servers.getServerOrNull(token));
	}
	
	@GetMapping("/my")
	public Collection<ProxyCreditalsDTO> getAllMyCreditals(
			@RequestBody UserDTO dto
			){
		return creditals.getMyCreditals(creditals.checkAuthUser(dto.getLogin(), dto.getToken()));
	}
	
	@Getter
	@Setter
	static class UserDTO{
		String login;
		String token;
	}
	
}
