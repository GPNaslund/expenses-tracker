package expenses_tracker.app.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionServiceTestConfig {
	@Bean
	public SessionService sessionService(SessionRepository repo) {
		return new SessionService(repo, "test");
	}
}
