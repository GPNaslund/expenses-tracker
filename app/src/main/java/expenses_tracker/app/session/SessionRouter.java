package expenses_tracker.app.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SessionRouter {

	@Bean
	public RouterFunction<ServerResponse> sessionRoutes(SessionHandler handler) {
		return RouterFunctions.route()
				.POST("session/logout", handler::logout)
				.build();
	}
}
