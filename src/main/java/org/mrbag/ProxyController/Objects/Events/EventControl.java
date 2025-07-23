package org.mrbag.ProxyController.Objects.Events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;
import org.springframework.stereotype.Service;

@Service
public class EventControl {

	volatile Map<String, LinkedList<IEvent>> events = new HashMap<String, LinkedList<IEvent>>();
	
	volatile Map<String, WebSocketRegulator> handler = new HashMap<String, WebSocketRegulator>();
	
	synchronized public void mountEvent(IEventSocket pxs, IEvent event) {
		if (!events.containsKey(pxs.getEventAccesKey())) {
			events.put(pxs.getEventAccesKey(), new LinkedList<IEvent>());
		}
		events.get(pxs.getEventAccesKey()).add(event);
		
	}
	
	synchronized public void mountEventExcute(IEventSocket pxs, IEvent event) {
		if (handler.containsKey(pxs.getEventAccesKey())) {
			if (!handler.get(pxs.getEventAccesKey()).isValid()) {
				unMountHandler(pxs);
			}
			else {
				event.process(handler.get(pxs.getEventAccesKey()));
				return;
			}
		}
		mountEvent(pxs, event);
	}
	
	public void mountHandler(IEventSocket pxs, WebSocketRegulator e) {
		handler.put(pxs.getEventAccesKey(), e);
		if (events.containsKey(pxs.getEventAccesKey())) {
			LinkedList<IEvent> evs = events.get(pxs.getEventAccesKey());
			while (!evs.isEmpty()) {
				evs.pop().process(e);
			}
			events.remove(pxs.getEventAccesKey());
		}
	}
	
	public void unMountHandler(IEventSocket pxs) {
		handler.remove(pxs.getEventAccesKey());
	}
	
}
