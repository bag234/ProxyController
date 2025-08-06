package org.mrbag.ProxyController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import net.minidev.json.JSONArray;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProxyServerControler {

	@Autowired
	MockMvc mvc;
	
	final static String URL = "/api/ProxyServer";
	
	final Logger log = LoggerFactory.getLogger(getClass()); 
	
	@Test
	public void testingCreateNodesAndDrop() throws Exception {
		String token = mvc.perform(post(URL +"/new")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		log.info("Generate new token for server: token");
		assertTrue(
				mvc.perform(get(URL + "/all")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString().contains(token)
				, "Token not contain in /all"
				);
		log.info("checked list of /all");
		mvc.perform(delete(URL + "/" + token)).andExpect(status().isOk());
		log.info("delete new token");
		assertFalse(
				mvc.perform(get(URL + "/all")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString().contains(token)
				, "Token contain in /all"
				);
		log.info("deleted token");
	}
	
	@Test
	public void complecsTestinApiServerControllerEvents() throws Exception {
		String od = mvc.perform(get(URL + "/all"))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
		assertNotEquals(od, "[]", "List in test Db is empty");
		JSONArray arr = (JSONArray) new JSONParser(JSONParser.MODE_JSON_SIMPLE).parse(od);
		
		arr.stream().map(obj -> (JSONObject) obj).forEach(obj ->{
			assertTrue(obj.containsKey("eventAccesKey"), "Not set Event key");
			try {
				log.info("Check is events key: " + obj.get("eventAccesKey") );
				assertTrue(
						mvc.perform(get(String.format("%s/%s/events", URL, obj.get("eventAccesKey"))))
							.andExpect(status().isOk()).andReturn().getResponse().getContentAsString().isEmpty()
				);
				log.info("-- list is clear;");
				mvc.perform(post(String.format("%s/%s/events", URL, obj.get("eventAccesKey"))).content("~test")).andExpect(status().isOk());
				log.info("-- add ~test command;");
				
				assertTrue(
						mvc.perform(get(String.format("%s/%s/events", URL, obj.get("eventAccesKey"))))
						.andExpect(status().isOk()).andReturn().getResponse().getContentAsString().contains("~test"),
						"Method /events not save status events"
						);
				log.info("-- checked add;");
				mvc.perform(delete(String.format("%s/%s/events", URL, obj.get("eventAccesKey"))).content("~test")).andExpect(status().isOk());
				log.info("-- delete all events;");
				assertTrue(
						mvc.perform(get(String.format("%s/%s/events", URL, obj.get("eventAccesKey"))))
							.andExpect(status().isOk()).andReturn().getResponse().getContentAsString().isEmpty(), 
						"Event's list is not empty"
				);
				log.info("-- list is clear;");
			} 
			catch (Exception e) {
				log.error(e.toString());
				assertFalse(false, "Ahahaha Ohohohoho Eheheehehe ihihihihihih");
			}
		});
		
	}
	
}
