package org.mrbag.ProxyController.Objects.Events;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;

public interface IEvent {

	public void process (WebSocketRegulator ws);
	
}
