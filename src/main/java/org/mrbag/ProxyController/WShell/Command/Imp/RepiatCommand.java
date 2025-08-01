package org.mrbag.ProxyController.WShell.Command.Imp;

import java.io.IOException;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;
import org.mrbag.ProxyController.WShell.Command.Command;
import org.mrbag.ProxyController.WShell.Command.ICommand;
//not wrong is secure method access ;) 
@Command("repiat")
public class RepiatCommand implements ICommand {

	@Override
	public void handle(String data, WebSocketRegulator regulator) throws IOException {
		regulator.sendMessage(data.replaceFirst("~repiat", "").trim());
	}

}
