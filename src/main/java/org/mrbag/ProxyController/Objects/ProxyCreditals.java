package org.mrbag.ProxyController.Objects;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "Nodes")
@NoArgsConstructor
@AllArgsConstructor
public class ProxyCreditals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	String login;
	
	String password;
	
	LocalDateTime toDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "server_id")
	ProxyServers ps;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	User user;
	
	@Builder.Default
	@Column(name = "continue")
	boolean isContinue = true;
	
}
