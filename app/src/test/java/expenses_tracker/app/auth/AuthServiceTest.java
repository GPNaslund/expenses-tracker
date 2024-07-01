package expenses_tracker.app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import expenses_tracker.app.model.RegisteredUser;
import expenses_tracker.app.model.UserCredentials;
import expenses_tracker.app.repository.RegisteredUserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(AuthService.class)
public class AuthServiceTest {
	@MockBean
	private RegisteredUserRepository repo;

	@Autowired
	private AuthService sut;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void validateCredentials_shouldReturnUserOnValidCredentials() {
		RegisteredUser user = new RegisteredUser("test", "test");
		Mockito.when(repo.getByUsername("test")).thenReturn(Mono.just(user));

		UserCredentials credentials = new UserCredentials("test", "test");
		Mono<RegisteredUser> returnedUser = sut.validateCredentials(credentials);

		StepVerifier.create(returnedUser)
				.expectNext(user)
				.verifyComplete();
	}

	@Test
	public void validateCredentials_shouldReturnEmptyMonoOnInvalidPassword() {
		RegisteredUser user = new RegisteredUser("test", "correct");
		Mockito.when(repo.getByUsername("test")).thenReturn(Mono.just(user));

		UserCredentials credentials = new UserCredentials("test", "wrong");
		Mono<RegisteredUser> returnedUser = sut.validateCredentials(credentials);

		StepVerifier.create(returnedUser)
				.expectNextCount(0)
				.verifyComplete();

	}

	@Test
	public void validateCredentials_shouldReturnEmptyMonoOnInvalidUsername() {
		Mockito.when(repo.getByUsername("test")).thenReturn(Mono.empty());

		UserCredentials credentials = new UserCredentials("test", "test");
		Mono<RegisteredUser> returnedUser = sut.validateCredentials(credentials);

		StepVerifier.create(returnedUser)
				.expectNextCount(0)
				.verifyComplete();
	}

}
