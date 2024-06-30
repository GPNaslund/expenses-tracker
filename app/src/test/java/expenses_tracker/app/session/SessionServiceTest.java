package expenses_tracker.app.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpCookie;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(SessionService.class)
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
	public void terminate_shouldReturnErrorOnNonExistingSession() {
		Mockito.when(repo.deleteSession(ArgumentMatchers.any(HttpCookie.class))
				.thenReturn(Mono.error(new IllegalArgumentException())));

		MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
		cookies.add("test", new HttpCookie("test", "test"));
		Mono<Void> result = sut.terminateSession(cookies);

		StepVerifier.create(result).expectError(IllegalArgumentException.class).verify();
	}
}
