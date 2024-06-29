package expenses_tracker.app.repository;

import org.springframework.stereotype.Repository;

import expenses_tracker.app.model.RegisteredUser;
import reactor.core.publisher.Mono;

@Repository
public class RegisteredUserRepository {

	public Mono<RegisteredUser> getByUsername(String username) {
		return Mono.empty();
	}
}
