package expenses_tracker.app.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpCookie;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(SessionService.class)
@Import(SessionServiceTestConfig.class)
public class SessionServiceTest {
	@MockBean
	private SessionRepository repo;

	@Autowired
	private SessionService sut;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void terminateSession_shouldReturnErrorOnNonExistingSession() {
		Mockito.doReturn(Mono.error(new IllegalArgumentException())).when(repo)
				.deleteSession(ArgumentMatchers.any(HttpCookie.class));

		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		cookies.add("test", new HttpCookie("test", "test"));
		Mono<Void> result = sut.terminateSession(cookies);

		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}

	@Test
	public void terminateSession_shouldReturnErrorOnInvalidCookie() {
		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		Mono<Void> result = sut.terminateSession(cookies);
		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}

	@Test
	public void terminateSession_shouldReturnEmptyMonoOnSuccess() {
		Mockito.doReturn(Mono.empty()).when(repo).deleteSession(ArgumentMatchers.any(HttpCookie.class));

		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		cookies.add("test", new HttpCookie("test", "test"));
		Mono<Void> result = sut.terminateSession(cookies);

		StepVerifier.create(result).expectNextCount(0).verifyComplete();
	}

	@Test
	public void validateSession_shouldReturnErrorOnNonExistingSession() {
		Mockito.doReturn(Mono.error(new IllegalArgumentException())).when(repo)
				.getSession(ArgumentMatchers.any(HttpCookie.class));

		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		cookies.add("test", new HttpCookie("test", "test"));
		Mono<Void> result = sut.validateSession(cookies);

		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}

	@Test
	public void validateSession_shouldReturnErrorOnInvalidCookie() {
		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		Mono<Void> result = sut.validateSession(cookies);
		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}

	@Test
	public void validateSession_shouldReturnEmptyMonoOnValidSession() {
		Mockito.doReturn(Mono.empty()).when(repo).getSession(ArgumentMatchers.any(HttpCookie.class));
		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		cookies.add("test", new HttpCookie("test", "test"));

		Mono<Void> result = sut.validateSession(cookies);

		StepVerifier.create(result).expectNextCount(0).verifyComplete();
	}
}
