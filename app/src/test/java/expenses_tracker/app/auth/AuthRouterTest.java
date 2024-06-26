package expenses_tracker.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(AuthRouter.class)
public class AuthRouterTest {
	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private AuthHandler authHandler;

	@BeforeEach
	public void setup() {
		Mockito.when(authHandler.handleLogin(any(LoginRequest.class))).thenReturn(Mono.empty());
	}

	@Test
	public void shouldCallLoginHandler() {
		webTestClient.post().uri("/auth/login")
				.contentType(APPLICATION_JSON)
				.bodyValue(new LoginRequest("test", "test"))
				.exchange()
				.expectStatus().isOk();
		Mockito.verify(authHandler).handleLogin(ArgumentMatchers.any(LoginRequest.class));
	}
}
