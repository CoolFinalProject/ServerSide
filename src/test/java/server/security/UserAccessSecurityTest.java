package server.security;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import server.controller.UserController;
import server.services.UserService;

class UserAccessSecurityTest {

	private MockMvc mockMvc;
	private UserService userService;

	@BeforeEach
	void setUp() {
		userService = mock(UserService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
	}

	@Test
	void getPreferencesUsesAuthenticatedUidOnly() throws Exception {
		when(userService.getUserPreferences("user-a"))
				.thenReturn(Map.of("POLITICS", 0.8f));

		mockMvc.perform(get("/users/preferences")
						.requestAttr("firebaseUid", "user-a"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.POLITICS").value(0.8f));

		verify(userService).getUserPreferences(eq("user-a"));
	}
}
