package expenses_tracker.app.auth;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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
					.flatMap(user -> ServerResponse.status(HttpStatus.OK).bodyValue(user))
					.switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
		})
				.switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).build())
				.onErrorResume(ClassCastException.class,
						e -> ServerResponse.status(HttpStatus.BAD_REQUEST).build());
	}

	public Mono<ServerResponse> logout(ServerRequest req) {
		MultiValueMap<String, HttpCookie> cookies = req.cookies();
		try {
			service.terminateSession(cookies);
			return ServerResponse.status(HttpStatus.OK).build();
		} catch (IllegalArgumentException e) {
			return ServerResponse.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
