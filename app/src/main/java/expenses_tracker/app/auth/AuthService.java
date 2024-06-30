package expenses_tracker.app.auth;

import org.springframework.stereotype.Service;

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
		Mono<RegisteredUser> registeredUser = repo.getByUsername(credentials.getUsername());
		return registeredUser.flatMap(user -> {
			if (credentials.getPassword().equals(user.getPassword())) {
				return registeredUser;
			}
			return Mono.empty();
		});
	}

}
