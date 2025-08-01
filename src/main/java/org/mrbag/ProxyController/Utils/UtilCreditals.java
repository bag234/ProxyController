package org.mrbag.ProxyController.Utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;

public class UtilCreditals {

	final static int COUNT_SIZE = 12;
	
	final static SecureRandom rand = new SecureRandom();
	
	public static String generateString(int lengh) {
		byte[] buf = new byte[lengh+6];
		rand.nextBytes(buf);
		return Base64.getEncoder().encodeToString(buf).replaceAll("[^a-zA-Z0-9]", "").substring(0, lengh);
	}
	
	public static ProxyCreditals newCridetals(LocalDateTime time) {
		return ProxyCreditals.builder()
				.login(generateString(COUNT_SIZE))
				.password(generateString(COUNT_SIZE))
				.toDate(time)
				.build();
	}

	public static String CreditalsToJSON(ProxyCreditals credital) {
		return String.format("{\"u\":\"%s\", \"p\":\"%s\", \"d\":\"%s\"}", 
				credital.getLogin(), credital.getPassword(), 
				credital.getToDate().toString());
	}
	
	public static String CreditalsToSimple(ProxyCreditals credital) {
		return String.format("%s@%s@%s", credital.getLogin(), credital.getPassword(), 
				credital.getToDate().toString());
	}
	
	public static String CreditalsToSimple(Collection<ProxyCreditals> creditals) {
		String buf = ""; 
		
		if (creditals.size() > 0)
			for(ProxyCreditals pxc : creditals)
				if (buf.isEmpty())
					buf = CreditalsToSimple(pxc);
				else 
					buf += "\n" + CreditalsToSimple(pxc);
				
		return buf;
	}
	
	public static String CreditalsToJSON(Collection<ProxyCreditals> creditals) {
		String buf = ""; 
		
		if (creditals.size() > 0)
			for(ProxyCreditals pxc : creditals)
				if (buf.isEmpty())
					buf = CreditalsToJSON(pxc);
				else 
					buf += ", " + CreditalsToJSON(pxc);
				
		return "[" + buf + "]";
	}
	
}
