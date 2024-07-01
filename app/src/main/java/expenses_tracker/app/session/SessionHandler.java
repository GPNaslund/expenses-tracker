package expenses_tracker.app.session;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class SessionHandler {

	private final SessionService service;

	public SessionHandler(SessionService service) {
		this.service = service;
	}

	public Mono<ServerResponse> logout(ServerRequest req) {
		MultiValueMap<String, HttpCookie> cookies = req.cookies();
		Mono<Void> result = service.terminateSession(cookies);
		return result.then(ServerResponse.status(HttpStatus.OK).build())
				.onErrorResume(IllegalArgumentException.class,
						e -> ServerResponse.status(HttpStatus.BAD_REQUEST).build());
	}
}
