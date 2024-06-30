package expenses_tracker.app.session;

import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class SessionService {

	public void terminateSession(MultiValueMap<String, HttpCookie> cookies) throws IllegalArgumentException {

	}
}
