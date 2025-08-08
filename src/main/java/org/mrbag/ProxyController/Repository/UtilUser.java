package org.mrbag.ProxyController.Repository;

import org.mrbag.ProxyController.Objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UtilUser {

	@PersistenceContext
	EntityManager manger;
	
	UserRep rep;
	
	@Autowired
	public void setRep(UserRep rep) {
		this.rep = rep;
	}
	
	public boolean canOpertationWriting(String token, double amount) {
		return rep.canOperationWriting(token, amount);
	}
	
	@Transactional
	synchronized public boolean operationWriting(String token, double amount) {
		if (amount > 0 )
			return false;
		
		User usr =(User) manger.createQuery(
				"SELECT u FROM User u WHERE u.token = :tkn")
				.setParameter(":tkn", token).getSingleResult();
		if (usr.writing(amount))
			return false;
		
		manger.persist(usr);		
		
		return true;
	}
	
	@Transactional
	public void operationRefill(String token, double amount) {
		rep.operationRefill(token, amount);
	}
	
	public User getActiveUser(String token) {
		return rep.getActiveUserFromToken(token);
	}
	
	public User getUserByLogin(String login) {
		return rep.findOneByLogin(login);
	}
	
	@Transactional
	public void unblockUser(String token) { 
		rep.unblockUser(token);
		
	}
	
	public void unblockUser(User u) {
		unblockUser(u.getToken());
	}
	@Transactional
	public void blockUser(String token) { 
		rep.blockUser(token);
		
	}
	
	public void blockUser(User u) {
		blockUser(u.getToken());
	}
	
	public void dropUser(String token) {
		User usr = rep.findOneByToken(token);
		if(usr != null)
			rep.delete(usr);
	}
	
	/** 
	 * Get User, and integrate Token, save in db and return users
	 */
	public User processNew(User u) {
		if (!u.isValid()) return null;
		u.configureToken();
		rep.save(u);
		return u;
	}
	
}
