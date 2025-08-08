package org.mrbag.ProxyController.Objects;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Principal")
public class User {

	@Id
	@GeneratedValue()
	long id;
	
	@Column(nullable = false, unique = true)
	String login;
	
	@Column(name = "token_access", nullable = false, unique = true, updatable = false)
	String token;
	
	double balance; 
	
	public boolean isValid() {
		if (login == null || login.isEmpty())
			return false;
		return true;
	}
	
	public void configureToken() {
		balance = 0;
		assert token == null: "Token must nullable";
		token = UUID.randomUUID().toString();
	}
	
	/*
	 * Simple method operation of balance, Add TODO all operation on using jpql
	 */
	synchronized public boolean writing(double amount) {
		if (amount > 0 && balance < amount) return false;
		
		balance -= amount;
		
		return true;
	}
	
	synchronized public boolean refill(double amount) {
		if (amount > 0 ) return false;
		
		balance += amount;
		
		return true;
	}
	
}
