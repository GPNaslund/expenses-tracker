package expenses_tracker.app.auth;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import expenses_tracker.app.model.UserCredentials;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

	private AuthService service;

	public AuthHandler(AuthService service) {
		this.service = service;
	}

	public Mono<ServerResponse> login(ServerRequest req) {
		Mono<UserCredentials> body = req.bodyToMono(UserCredentials.class);
		return body.flatMap(credentials -> {
			return service.validateCredentials(credentials)
					.flatMap(user -> ServerResponse.status(HttpStatus.OK).bodyValue(user));
		});
	}

	public Mono<ServerResponse> logout(ServerRequest req) {
		return Mono.empty();
	}
}
