package expenses_tracker.app.auth;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import expenses_tracker.app.model.RegisteredUser;
import expenses_tracker.app.model.UserCredentials;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(AuthHandler.class)
public class AuthHandlerTest {
	@MockBean
	private AuthService service;

	@Autowired
	private AuthHandler sut;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void login_shouldReturn200OnValidCredentials() {
		RegisteredUser user = new RegisteredUser("test", "test");
		Mockito.when(service.validateCredentials(ArgumentMatchers.any(UserCredentials.class)))
				.thenReturn(Mono.just(user));
		UserCredentials credentials = new UserCredentials("test", "test");
		authHandlerTester(Mono.just(credentials), HttpStatus.OK);
	}

	@Test
	public void login_shouldReturn401OnInvalidCredentials() {
		Mockito.when(service.validateCredentials(ArgumentMatchers.any(UserCredentials.class)))
				.thenReturn(Mono.empty());
		UserCredentials credentials = new UserCredentials("test", "test");

		authHandlerTester(Mono.just(credentials), HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void login_shouldReturn400OnInvalidRequest() {
		authHandlerTester(Mono.empty(), HttpStatus.BAD_REQUEST);
	}

	private <T> void authHandlerTester(Mono<T> bodyVal, HttpStatus expectedStatus) {
		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(bodyVal);

		Mono<ServerResponse> res = sut.login(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(expectedStatus))
				.verifyComplete();
	}

}
