package org.mrbag.ProxyController.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.User;
import org.mrbag.ProxyController.Objects.Dute.ProxyCreditalsDTO;
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
	
	@Query("SELECT pc.login, pc.password, pc.toDate, pc.ps, pc.isContinue FROM ProxyCreditals pc WHERE pc.user = :usr")
	Collection<ProxyCreditalsDTO> findAllByUser(@Param("usr") User usr);
	
	@Query("Select pc FROM ProxyCreditals pc WHERE pc.login = :lgn AND pc.password = :psw AND pc.ps = "
			+ "(SELECT ps FROM ProxyServers ps WHERE ps.token = :pxs)")
	ProxyCreditals findByCustomforContinue(
				@Param("lgn") String login,
				@Param("psw") String password,
				@Param("pxs") String token
			);
	

//	void dropAllBeforeDate(@Param("time") LocalDateTime ldt);
	
}
