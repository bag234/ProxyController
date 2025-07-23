package org.mrbag.ProxyController.WShell.Command;

import java.io.IOException;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;

@Command("test")
public class TestCommand implements ICommand{

	@Override
	public void handle(String data, WebSocketRegulator regulator) throws IOException  {
		regulator.sendMessage("Some test");
	}

}
