package server.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.FilterChain;

class FirebaseAuthFilterTest {

	@Test
	void rejectsRequestWithoutBearerToken() throws Exception {
		FirebaseAuthFilter filter = new FirebaseAuthFilter();
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/preferences");
		MockHttpServletResponse response = new MockHttpServletResponse();
		FilterChain chain = mock(FilterChain.class);

		filter.doFilter(request, response, chain);

		assertEquals(401, response.getStatus());
		verify(chain, never()).doFilter(any(), any());
	}
}
