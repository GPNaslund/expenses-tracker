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
	public void shouldReturn200OnLoginWithValidCredentials() {
		RegisteredUser user = new RegisteredUser();
		Mockito.when(service.validateCredentials(ArgumentMatchers.any(UserCredentials.class)))
				.thenReturn(Mono.just(user));

		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(Mono.just(new UserCredentials("test", "test")));

		Mono<ServerResponse> res = sut.login(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(HttpStatus.OK))
				.verifyComplete();
	}

	@Test
	public void shouldReturn401OnLoginWithInvalidCredentials() {
		Mockito.when(service.validateCredentials(ArgumentMatchers.any(UserCredentials.class)))
				.thenReturn(Mono.empty());

		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(Mono.just(new UserCredentials("test", "test")));

		Mono<ServerResponse> res = sut.login(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(HttpStatus.UNAUTHORIZED))
				.verifyComplete();
	}

	@Test
	public void shouldReturn400OnLoginWithInvalidRequest() {
		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(Mono.just("Test"));

		Mono<ServerResponse> res = sut.login(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(HttpStatus.BAD_REQUEST))
				.verifyComplete();
	}

	@Test
	public void shouldReturn200OnSuccessfulLogout() {
		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(Mono.empty());

		Mono<ServerResponse> res = sut.logout(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode().equals(HttpStatus.OK))
				.verifyComplete();
	}
}
