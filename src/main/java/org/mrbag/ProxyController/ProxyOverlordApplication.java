package org.mrbag.ProxyController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ProxyOverlordApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyOverlordApplication.class, args);
	}

}
