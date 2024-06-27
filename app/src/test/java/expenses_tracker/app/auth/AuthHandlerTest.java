package expenses_tracker.app.auth;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import io.netty.handler.codec.http.HttpMethod;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class AuthHandlerTest {
	@Mock
	private AuthService service;

	@InjectMocks
	private AuthHandler sut;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldReturn201OnValidCredentials() {
		Mockito.when(service.login(ArgumentMatchers.any(UserCredentials.class))
				.thenReturn(Mono.just(ArgumentMatchers.any(RegisteredUser.class))));

		String jsonBody = "{\"username\": \"test\", \"password\":\"test\"}";
		DataBuffer buffer = new DefaultDataBufferFactory().wrap(jsonBody.getBytes());
		ServerRequest req = MockServerRequest.builder()
				.method(org.springframework.http.HttpMethod.POST)
				.uri(URI.create("/test"))
				.body(Mono.just(buffer));

		Mono<ServerResponse> res = sut.login(req);

		StepVerifier.create(res)
				.expectNextMatches(serverResponse -> serverResponse.statusCode()
						.equals(HttpStatus.CREATED))
				.verifyComplete();
	}
}
