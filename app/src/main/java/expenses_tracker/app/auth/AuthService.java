package expenses_tracker.app.auth;

import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import expenses_tracker.app.model.RegisteredUser;
import expenses_tracker.app.model.UserCredentials;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

	public Mono<RegisteredUser> validateCredentials(UserCredentials credentials) {
		return Mono.empty();
	}

	public void terminateSession(MultiValueMap<String, HttpCookie> cookies) throws IllegalArgumentException {

	}
}
