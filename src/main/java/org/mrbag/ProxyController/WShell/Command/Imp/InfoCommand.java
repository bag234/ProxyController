package org.mrbag.ProxyController.WShell.Command.Imp;

import java.io.IOException;

import org.json.JSONObject;
import org.mrbag.ProxyController.WShell.WebSocketRegulator;
import org.mrbag.ProxyController.WShell.Command.Command;
import org.mrbag.ProxyController.WShell.Command.ICommand;

@Command("info")
public class InfoCommand implements ICommand {

	@Override
	public void handle(String data, WebSocketRegulator regulator) throws IOException {
		if(data.indexOf('{') < 1 || data.lastIndexOf('}') < 1) {
			regulator.sendMessage("~info format not valid");
			return;
		}
		String sp = data.substring(data.indexOf('{'), data.lastIndexOf('}') +1);
		regulator.updateInfo(new JSONObject(sp));
	}

}
