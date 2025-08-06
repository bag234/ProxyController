package org.mrbag.ProxyController.Repository;

import org.mrbag.ProxyController.Objects.ProxyServers;
import org.mrbag.ProxyController.Objects.StatusServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProxyServerRep extends JpaRepository<ProxyServers, Long> {

	boolean existsByToken(String token);
	
	ProxyServers findOneByToken(String token);
		
	@Modifying
	@Query("UPDATE ProxyServers px SET px.status = :st WHERE px.token = :tok")
	void updateStatusByToken(@Param("tok") String token, @Param("st") StatusServer status);
	
	@Modifying
	@Query("UPDATE ProxyServers px SET px.status = :st WHERE px.status != 'NONE'")
	void updateStatusAll(@Param("st") StatusServer status);
	
	@Modifying
	@Query("DELETE FROM ProxyServers px WHERE px.token = :tok")
	void dropOneByToken(@Param("tok") String token);
}
