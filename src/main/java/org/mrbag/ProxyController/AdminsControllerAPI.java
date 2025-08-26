package org.mrbag.ProxyController;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminsControllerAPI {

	@GetMapping("/servers")
	public ResponseEntity<Resource> getProxyServerAdminPage() {
		Resource resource = new ClassPathResource("templates/admin-server.html");
		return ResponseEntity.ok().body(resource);
	}

}
