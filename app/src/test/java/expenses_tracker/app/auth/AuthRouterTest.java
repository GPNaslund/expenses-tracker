package expenses_tracker.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;

import reactor.core.publisher.Mono;

@WebFluxTest(AuthRouter.class)
public class AuthRouterTest {
	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private AuthHandler authHandler;

	@BeforeEach
	public void setup() {
		Mockito.when(authHandler.login(ArgumentMatchers.any(ServerRequest.class)))
				.thenReturn(Mono.empty());
	}

	@Test
	public void shouldCallLoginHandler() {
		webTestClient.post().uri("/auth/login")
				.exchange()
				.expectStatus().isOk();
		Mockito.verify(authHandler).login(ArgumentMatchers.any(ServerRequest.class));
	}
}
