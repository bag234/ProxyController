package org.mrbag.ProxyController.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

	long id;
	
	String login;
	
	String token;
	
}
