package org.mrbag.ProxyController.Objects.Events.Imp;

import java.io.IOException;

import org.mrbag.ProxyController.Objects.Events.IEvent;
import org.mrbag.ProxyController.WShell.WebSocketRegulator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RepeatComand implements IEvent {

	String command;
	
	@Override
	public void process(WebSocketRegulator ws) {
		try {
			ws.sendMessage(command);
		} catch (IOException e) {}
	}

	@Override
	public String presnt() {
		return "send command: " + command;
	}
	
	public static RepeatComand New(String command) {
		return new RepeatComand(command);
	}

}
