package org.mrbag.ProxyController.WShell.Command;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.mrbag.ProxyController.WShell.WebSocketRegulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

class NotFoundCommand implements ICommand{

	@Override
	public void handle(String data, WebSocketRegulator regulator) throws IOException {
		regulator.sendMessage(String.format("~not Command not Found: %s;", data));
	}
	
}

@Service
public class CommandDispatcher {

	private static final Logger log = LoggerFactory.getLogger(CommandDispatcher.class);
	
	ApplicationContext context;
	
	Map<String, ICommand> commands = null;
	
	private final NotFoundCommand nfcom = new NotFoundCommand();
	
	public CommandDispatcher() {
//		commands = new HashMap<String, ICommand>();
	}
	
	@Autowired
	public void setContext(ApplicationContext context) {
		this.context = context;
		
		Map<String, Object> raw = context.getBeansWithAnnotation(Command.class);
		if (raw == null) {
			log.error("Command bean loader is Null");
			return;
		}

		commands = raw.entrySet().stream().map(ke -> ke.getValue())
		.filter(cls -> {
			if (Arrays.asList(cls.getClass().getGenericInterfaces()).contains(ICommand.class))
				return true;
			log.warn(String.format("Command filter [Been:%s]: not declarate interface(List of inteface: %s)", 
					cls.getClass().getSimpleName(), Arrays.toString(cls.getClass().getGenericInterfaces())));
			return false;
		})
		.collect(Collectors.toMap(cls -> cls.getClass().getAnnotation(Command.class).value().toLowerCase(), cls -> (ICommand)cls));
	}
	
	public boolean processCommand(String data, WebSocketRegulator regulator) {
		if (!regulator.isValid() || !data.strip().startsWith("~") || data.length() < 4)
			return false;
	
		String command;
		if (data.strip().indexOf(" ") > 0)
			command = data.strip().substring(1, data.strip().indexOf(" "));
		else
			command = data.strip().substring(1);
		
		try {
			commands.getOrDefault(command, nfcom).handle(data, regulator);
			return true;
		} catch (IOException e) {
			log.warn("Error process command's", e);
		}
	
		return false;
	}
}
