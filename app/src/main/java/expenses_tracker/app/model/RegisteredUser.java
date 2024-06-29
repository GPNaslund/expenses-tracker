package expenses_tracker.app.model;

public class RegisteredUser {
	private Long id;

	private String username;
	private String password;

	public RegisteredUser(String username, String password) {
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
