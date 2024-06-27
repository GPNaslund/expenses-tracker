package expenses_tracker.app.model;

public class UserCredentials {
	private String username;
	private String password;

	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
}
