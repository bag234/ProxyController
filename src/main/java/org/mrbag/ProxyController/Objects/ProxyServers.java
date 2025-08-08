package org.mrbag.ProxyController.Objects;

import org.json.JSONObject;
import org.mrbag.ProxyController.Objects.Events.IEventSocket;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Servers")
public class ProxyServers implements IEventSocket {

	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "Named-Server")
	String name; 
	
	String ip;
	
	int port;
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	TypeProxy type = TypeProxy.NONE;
	
	@Enumerated(EnumType.STRING)
	@Builder.Default
	StatusServer status = StatusServer.NONE;
	
	@Column(unique = true)
	String token;
	
 	double cost;
	
	public void setFromJson(JSONObject obj) {
		if(obj.keySet().contains("name")) {
			name = obj.getString("name");
		}
		if(obj.keySet().contains("ip")) {
			ip = obj.getString("ip");
		}
		if(obj.keySet().contains("port")) {
			port = obj.getInt("port");
		}
		if(obj.keySet().contains("type")) {
			type = TypeProxy.valueOf(obj.getString("type"));
		}
	}

	@Override
	public String getEventAccesKey() {
		return token;
	}
	
}
