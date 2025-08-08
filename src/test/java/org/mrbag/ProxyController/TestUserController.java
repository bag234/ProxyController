package org.mrbag.ProxyController;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
public class TestUserController {

	@Autowired
	MockMvc mvc;
	
	final static String URL = "/api/User";
	
	final static String USR_NEW = """
			{"login":"only_for_te2tt"}
			""";
	
	static String sel_tkn = "";
	
	
	@BeforeAll
	public void createTestUser() throws Exception {
		log.info("Start creating User");
		String token = mvc.perform(post("/api/User/new").content(USR_NEW).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertFalse(token.isEmpty(), "token retun null");
		log.info("Create test user " + USR_NEW + " token: " + token);
		sel_tkn = token;
	}
	
	@Test
	public void testLoginUser() throws Exception{
		String user = mvc.perform(post(URL + "/login").content(USR_NEW).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertTrue(user.contains(sel_tkn), "user not can unblock");
	}
	
	@Test
	public void testBlockUser() throws Exception {
		String user = mvc.perform(get(URL + "/" + sel_tkn)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertTrue(user.contains(sel_tkn), "user not can block");
		mvc.perform(get(URL + "/" + sel_tkn + "/block")).andExpect(status().isOk());
		user = mvc.perform(get(URL + "/" + sel_tkn)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertFalse(user.contains(sel_tkn), "user not can unblock");
		mvc.perform(get(URL + "/" + sel_tkn + "/block")).andExpect(status().isOk());
		log.info("Checked block and unblock user");
	}
	
	@Test
	public void testBalanceOperation() throws Exception{
		String answ = mvc.perform(patch(URL + "/" + sel_tkn + "/balance?amount=100")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertFalse(answ.equals("true"), "Balance contain another sum");
		
		answ = mvc.perform(post(URL + "/" + sel_tkn + "/balance?amount=100")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertTrue(answ.equals("true"), "Balance not add sum");
		
		answ = mvc.perform(patch(URL + "/" + sel_tkn + "/balance?amount=100")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertTrue(answ.equals("true"), "Balance not minus sum");
		
		answ = mvc.perform(get(URL + "/" + sel_tkn + "/balance?amount=100")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertFalse(answ.equals("true"), "Balance not minus sum");
		
	}
	
	@AfterAll
	public void deleteTestUser() throws Exception {
		mvc.perform(delete(URL + "/" + sel_tkn)).andExpect(status().isOk());
		String token = mvc.perform(get(URL + "/" + sel_tkn)).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertTrue(token.isEmpty(), "Token not delete: " + sel_tkn);
		log.info("Delete testing user");
	}
	
}
