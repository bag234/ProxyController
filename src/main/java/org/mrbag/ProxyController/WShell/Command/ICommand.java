package org.mrbag.ProxyController.WShell.Command;

import java.io.IOException;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;

public interface ICommand {

	void handle(String data, WebSocketRegulator regulator) throws IOException;
	
}
