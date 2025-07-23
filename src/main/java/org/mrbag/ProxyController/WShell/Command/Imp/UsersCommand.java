package org.mrbag.ProxyController.WShell.Command.Imp;

import java.io.IOException;

import org.mrbag.ProxyController.Utils.UtilCreditals;
import org.mrbag.ProxyController.WShell.WebSocketRegulator;
import org.mrbag.ProxyController.WShell.Command.Command;
import org.mrbag.ProxyController.WShell.Command.ICommand;

@Command("users")
public class UsersCommand implements ICommand {

	@Override
	public void handle(String data, WebSocketRegulator regulator) throws IOException {
		regulator.sendMessage(UtilCreditals.CreditalsToJSON(regulator.getActiveUser()));
	}

}
