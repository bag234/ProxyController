package org.mrbag.ProxyController.Repository;

import org.mrbag.ProxyController.Objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRep extends JpaRepository<User, Long>{

	// maybe make simples, remove case block 
	@Query("SELECT (u.balance >= :amnt AND :amnt > 0) FROM User u WHERE u.token = :tkn")
	public boolean canOperationWriting(
			@Param("tkn") String token, 
			@Param("amnt") double amount);
	
	@Modifying
	@Deprecated
	@Query ("UPDATE User u SET u.balance = u.balance - :amnt WHERE u.token = :tkn AND :amnt > 0")
	void operationWriting(
			@Param("tkn") String token, 
			@Param("amnt") double amount);
	
	
	@Modifying
	@Query ("UPDATE User u SET u.balance = u.balance + :amnt WHERE u.token = :tkn AND :amnt > 0")
	void operationRefill(
			@Param("tkn") String token, 
			@Param("amnt") double amount);
	
	@Query("SELECT u FROM User u WHERE u.token = :tkn and u.balance >= 0")
	User getActiveUserFromToken(@Param("tkn") String token);
	
	@Modifying
	@Query ("UPDATE User u SET u.balance = -u.balance WHERE u.token = :tkn AND u.balance >= 0")
	void blockUser(
			@Param("tkn") String token
			);
	
	@Modifying
	@Query ("UPDATE User u SET u.balance = -u.balance WHERE u.token = :tkn AND u.balance < 0")
	void unblockUser(
			@Param("tkn") String token
			);
	
	User findOneByLogin(String login);
	
	User findOneByToken(String token);
}
