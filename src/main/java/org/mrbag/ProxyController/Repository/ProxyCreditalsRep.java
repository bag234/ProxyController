package org.mrbag.ProxyController.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.ProxyServers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyCreditalsRep extends JpaRepository<ProxyCreditals,Long> {
	
	@Query("SELECT pc FROM ProxyCreditals pc WHERE pc.ps = :pxs AND (pc.toDate IS NULL OR pc.toDate > CURRENT_DATE)")
	Collection<ProxyCreditals> findAllByProxyServersFiltredByTime(@Param("pxs") ProxyServers pxs);

	@Query("SELECT pc FROM ProxyCreditals pc WHERE pc.ps = :pxs")
	Collection<ProxyCreditals> findAllByProxyServers(@Param("pxs")ProxyServers pxs);
	

//	void dropAllBeforeDate(@Param("time") LocalDateTime ldt);
	
}
