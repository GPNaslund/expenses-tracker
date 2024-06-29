package expenses_tracker.app.model;

public class RegisteredUser {

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

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}

		if (!(other instanceof RegisteredUser)) {
			return false;
		}

		RegisteredUser o = (RegisteredUser) other;

		return this.username.equals(o.getUsername()) && this.password.equals(o.getPassword());
	}
}
