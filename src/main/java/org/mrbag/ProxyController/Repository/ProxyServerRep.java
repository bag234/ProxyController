package org.mrbag.ProxyController.Repository;

import org.mrbag.ProxyController.Objects.ProxyServers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProxyServerRep extends JpaRepository<ProxyServers, Long> {

	boolean existsByToken(String tiken);
	
}
