package expenses_tracker.app.session;

import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public class SessionRepository {

	public Mono<Void> deleteSession(HttpCookie cookie) {
		return Mono.empty();
	}
}
