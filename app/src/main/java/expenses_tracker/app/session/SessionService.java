package expenses_tracker.app.session;

import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;

@Service
public class SessionService {
	private SessionRepository repo;

	public SessionService(SessionRepository repo) {
		this.repo = repo;
	}

	public Mono<Void> terminateSession(MultiValueMap<String, HttpCookie> cookies) {
		return Mono.empty();
	}
}
