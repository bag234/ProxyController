package org.mrbag.ProxyController.Objects.Events;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;
import org.springframework.stereotype.Service;

@Service
public class EventControl {

	volatile Map<String, LinkedList<IEvent>> events = new ConcurrentHashMap<String, LinkedList<IEvent>>();
	
	volatile Map<String, WebSocketRegulator> handler = new ConcurrentHashMap<String, WebSocketRegulator>();
	
	synchronized public Collection<IEvent> getAllMountEvents(String key){
		return events.getOrDefault(key, null);
	}
	
	public boolean handlerIsMount(String key) { 
		return handler.containsKey(key);
	}
	
	synchronized private void mountEvent(IEventSocket pxs, IEvent event) {
		if (!events.containsKey(pxs.getEventAccesKey())) {
			events.put(pxs.getEventAccesKey(), new LinkedList<IEvent>());
		}
		events.get(pxs.getEventAccesKey()).add(event);
	}
	
	public boolean dropEvents(String key) {
		return events.remove(key) != null;
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
	/**
	 * Add event message to list event if list events already contain key access
	 * @param pxs - key to access of interface 
	 * @param event - mount event 
	 * @return success of the operation
	 */
	public boolean mountLazyEvent(IEventSocket pxs, IEvent event) {
		if(events.containsKey(pxs.getEventAccesKey())) {
			mountEventExcute(pxs, event);
			return true;
		}
		return false;
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
