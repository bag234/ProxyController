package org.mrbag.ProxyController.Repository;

import java.util.Collection;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.ProxyServers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilCreditals {

	ProxyCreditalsRep creditals;
	
	@Autowired
	public void setCreditals(ProxyCreditalsRep creditals) {
		this.creditals = creditals;
	}
	
	public Collection<ProxyCreditals> allUserPXS(ProxyServers pxs){
		return creditals.findAllByProxyServersFiltredByTime(pxs) ;
	}
	
}
