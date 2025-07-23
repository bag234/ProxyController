package org.mrbag.ProxyController.Objects.Events.Imp;

import java.io.IOException;

import org.mrbag.ProxyController.Objects.ProxyCreditals;
import org.mrbag.ProxyController.Objects.Events.IEvent;
import org.mrbag.ProxyController.Utils.UtilCreditals;
import org.mrbag.ProxyController.WShell.WebSocketRegulator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserEvent implements IEvent {
	
	ProxyCreditals user;

	@Override
	public void process(WebSocketRegulator ws) {
		try {
			ws.sendMessage("~user " + UtilCreditals.CreditalsToJSON(user));
		} catch (IOException e) {
			throw new UnsupportedOperationException("Add VALIDATION: " + e);
		}
	}

}
