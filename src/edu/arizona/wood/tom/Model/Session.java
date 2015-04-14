package edu.arizona.wood.tom.model;

import java.util.ArrayList;
import java.util.List;

public class Session {
	public static Session defaultInstance;
	private User loggedInUser;
	private ArrayList<String> availableQuestionIds;

	private Session() {
		this.loggedInUser = null;
		this.availableQuestionIds = new ArrayList<String>();
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

	public void setAvailableQuestions(List<String> questionIdList) {
		this.availableQuestionIds = new ArrayList<String>(questionIdList);
	}

	public void addAvailableQuestion(String qid) {
		this.availableQuestionIds.add(qid);
	}

	public ArrayList<String> getAvailableQuestions() {
		return this.availableQuestionIds;
	}
}