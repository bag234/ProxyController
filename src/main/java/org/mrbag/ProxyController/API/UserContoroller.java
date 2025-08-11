package org.mrbag.ProxyController.API;

import org.mrbag.ProxyController.Objects.User;
import org.mrbag.ProxyController.Repository.UtilUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Регистрация и авторизация пользователей
 */
@RestController
@RequestMapping("/api/User")
@Slf4j
public class UserContoroller {

	UtilUser util; 
	
	@Autowired
	public void setUtil(UtilUser util) {
		this.util = util;
	}
	
	@GetMapping("/{token}/balance")
	public boolean operationWriting(
			@PathVariable("token") String token, 
			@RequestParam(name = "amount", defaultValue = "&") String amount) {
		if (amount.equals("&"))
			return false;
		return util.operationWriting(token, Double.parseDouble(amount));
	}
	
	@PostMapping("/{token}/balance")
	public boolean operationRefill(
			@PathVariable("token") String token, 
			@RequestParam(name = "amount", defaultValue = "&") String amount) {
		if (amount.equals("&"))
			return false;
		util.operationRefill(token, Double.parseDouble(amount));
		return true;
	}
	
	@PatchMapping("/{token}/balance")
	public boolean canoperationWriting(
			@PathVariable("token") String token, 
			@RequestParam(name = "amount", defaultValue = "&") String amount) {
		if (amount.equals("&"))
			return false;
		return util.canOpertationWriting(token, Double.parseDouble(amount));
	}
	
	@PostMapping("/new")
	public String registerNewUser(@RequestBody LoginRequest login) {
		log.warn("[debug] is simple implementation");
		if (login == null || login.getLogin().isEmpty())
			return null;
		
		User u = User.builder().login(login.getLogin()).build();
		return util.processNew(u).getToken();
	
	}
	
	@PostMapping("/login")
	public User getLoginUser(@RequestBody LoginRequest login) {
		log.warn("[debug] is simple implementation");
		if (login == null || login.getLogin().isEmpty())
			return null;
		
		return util.getUserByLogin(login.getLogin());

	}
	
	@GetMapping("/{token}")
	public User getInfo(@PathVariable("token") String token) {
		return util.getActiveUser(token);
	}
	
	@GetMapping("/{token}/block")
	public void blockUser(@PathVariable("token") String token) {
		util.blockUser(token);
	}
	
	@GetMapping("/{token}/unblock")
	public void unblockUser(@PathVariable("token") String token) {
		util.unblockUser(token);
	}

	@DeleteMapping("/{token}")
	public void deleteUser(@PathVariable("token") String token) {
		util.dropUser(token);
	}
	
	@Getter
	@Setter
	static class LoginRequest {
        private String login;
    }
	
}
