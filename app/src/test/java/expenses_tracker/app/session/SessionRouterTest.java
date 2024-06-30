package expenses_tracker.app.session;

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

@WebFluxTest(SessionRouter.class)
public class SessionRouterTest {
	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private SessionHandler handler;

	@BeforeEach
	public void setup() {
		Mockito.when(handler.logout(ArgumentMatchers.any(ServerRequest.class)))
				.thenReturn(Mono.empty());
	}

	@Test
	public void shouldCallAuthHandlerOnLogoutReq() {
		webTestClient.post().uri("/session/logout")
				.exchange()
				.expectStatus().isOk();
		Mockito.verify(handler).logout(ArgumentMatchers.any(ServerRequest.class));
	}

}
