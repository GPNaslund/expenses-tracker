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
import org.springframework.util.MultiValueMap;
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
		UserCredentials credentials = new UserCredentials("test", "test");
		authHandlerTester(AuthHandlerMethod.LOGIN, Mono.just(credentials), HttpStatus.OK);
	}

	@Test
	public void shouldReturn401OnLoginWithInvalidCredentials() {
		Mockito.when(service.validateCredentials(ArgumentMatchers.any(UserCredentials.class)))
				.thenReturn(Mono.empty());
		UserCredentials credentials = new UserCredentials("test", "test");

		authHandlerTester(AuthHandlerMethod.LOGIN, Mono.just(credentials), HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void shouldReturn400OnLoginWithInvalidRequest() {
		authHandlerTester(AuthHandlerMethod.LOGIN, Mono.empty(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void shouldReturn200OnSuccessfulLogout() {
		authHandlerTester(AuthHandlerMethod.LOGOUT, Mono.empty(), HttpStatus.OK);
	}

	@Test
	public void shouldReturn400OnInvalidLogout() {
		Mockito.doThrow(new IllegalArgumentException())
				.when(service).terminateSession(ArgumentMatchers.any(MultiValueMap.class));

		authHandlerTester(AuthHandlerMethod.LOGOUT, Mono.empty(), HttpStatus.BAD_REQUEST);
	}

	private <T> void authHandlerTester(AuthHandlerMethod method, Mono<T> bodyVal, HttpStatus expectedStatus) {
		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(bodyVal);

		Mono<ServerResponse> res;

		if (method == AuthHandlerMethod.LOGIN) {
			res = sut.login(req);
		} else {
			res = sut.logout(req);
		}

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(expectedStatus))
				.verifyComplete();
	}

}
