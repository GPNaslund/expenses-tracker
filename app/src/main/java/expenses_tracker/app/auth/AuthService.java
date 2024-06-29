package expenses_tracker.app.auth;

import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import expenses_tracker.app.model.RegisteredUser;
import expenses_tracker.app.model.UserCredentials;
import expenses_tracker.app.repository.RegisteredUserRepository;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
	private RegisteredUserRepository repo;

	public AuthService(RegisteredUserRepository repo) {
		this.repo = repo;
	}

	public Mono<RegisteredUser> validateCredentials(UserCredentials credentials) {
		return repo.getByUsername(credentials.getUsername());
	}

	public void terminateSession(MultiValueMap<String, HttpCookie> cookies) throws IllegalArgumentException {

	}
}
