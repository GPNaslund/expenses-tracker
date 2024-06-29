package expenses_tracker.app.repository;

import expenses_tracker.app.model.RegisteredUser;
import reactor.core.publisher.Mono;

public class RegisteredUserRepository {

	public Mono<RegisteredUser> getByUsername(String username) {
		return Mono.empty();
	}
}
