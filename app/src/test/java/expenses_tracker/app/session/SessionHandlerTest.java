package expenses_tracker.app.session;

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

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(SessionHandler.class)
public class SessionHandlerTest {

	@MockBean
	private SessionService service;

	@Autowired
	private SessionHandler sut;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldReturn200OnSuccessfulLogout() {
		serviceHandlerTester(Mono.empty(), HttpStatus.OK);
	}

	@Test
	public void shouldReturn400OnInvalidLogout() {
		Mockito.doReturn(Mono.error(new IllegalArgumentException()))
				.when(service).terminateSession(ArgumentMatchers.any(MultiValueMap.class));

		serviceHandlerTester(Mono.empty(), HttpStatus.BAD_REQUEST);
	}

	private <T> void serviceHandlerTester(Mono<T> bodyVal, HttpStatus expectedStatus) {
		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(bodyVal);

		Mono<ServerResponse> res = sut.logout(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(expectedStatus))
				.verifyComplete();
	}
}
