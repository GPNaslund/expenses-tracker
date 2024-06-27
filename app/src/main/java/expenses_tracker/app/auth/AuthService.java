package expenses_tracker.app.auth;

import org.springframework.stereotype.Service;

import expenses_tracker.app.model.RegisteredUser;
import expenses_tracker.app.model.UserCredentials;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

	public Mono<RegisteredUser> validateCredentials(UserCredentials credentials) {
		return Mono.empty();
	}
}
