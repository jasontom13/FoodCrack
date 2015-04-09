package edu.arizona.wood.tom.model;

public class Session {
	public static Session defaultInstance;
	public User loggedInUser;

	private Session() {
		this.loggedInUser = null;
	}

	public static Session getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new Session();
		}

		return defaultInstance;
	}

	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	}

	public User getLoggedInUser() {
		return this.loggedInUser;
	}
}