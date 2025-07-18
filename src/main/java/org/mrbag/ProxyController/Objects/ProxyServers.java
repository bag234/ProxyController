package org.mrbag.ProxyController.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "Servers")
public class ProxyServers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Named-Server")
	String name; 
	
	String ip;
	
	int port;
	
	@Enumerated(EnumType.STRING)
	TypeProxy type;
	
	@Enumerated(EnumType.STRING)
	StatusServer status;
	
	@Column(unique = true)
	String token;
	
}
