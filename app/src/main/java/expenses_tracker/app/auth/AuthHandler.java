package expenses_tracker.app.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

	public Mono<ServerResponse> login(ServerRequest req) {
		return Mono.empty();
	}

	public Mono<ServerResponse> logout(ServerRequest req) {
		return Mono.empty();
	}
}
