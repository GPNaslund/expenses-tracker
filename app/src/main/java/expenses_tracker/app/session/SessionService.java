package expenses_tracker.app.session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Mono;

@Service
public class SessionService {
	private SessionRepository repo;
	private String sessionCookieName;

	public SessionService(SessionRepository repo,
			@Value("${environment.session_cookie_name}") String sessionCookieName) {
		this.repo = repo;
		this.sessionCookieName = sessionCookieName;
	}

	public Mono<Void> terminateSession(MultiValueMap<String, HttpCookie> cookies) {
		HttpCookie sessionCookie = cookies.getFirst(sessionCookieName);
		if (sessionCookie == null) {
			return Mono.error(new IllegalArgumentException());
		}
		Mono<Void> result = repo.deleteSession(sessionCookie);
		return result.onErrorResume(Exception.class,
				e -> Mono.error(new IllegalArgumentException()));
	}

	public Mono<Void> validateSession(MultiValueMap<String, HttpCookie> cookies) {
		HttpCookie sessionCookie = cookies.getFirst(sessionCookieName);
		if (sessionCookie == null) {
			return Mono.error(new IllegalArgumentException());
		}
		return repo.getSession(sessionCookie);
	}
}
