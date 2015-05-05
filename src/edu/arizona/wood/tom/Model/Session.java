package edu.arizona.wood.tom.model;

import java.util.ArrayList;
import java.util.List;

import edu.arizona.wood.tom.DatabaseHelper;

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
	
	public Statistics getStats()
	{
		return DatabaseHelper.getDefaultInstance().getUserStatistics(loggedInUser.getUsername());
	}
	
	public Achievements getAchievements()
	{
		return DatabaseHelper.getDefaultInstance().getUserAchievements(loggedInUser.getUsername());
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
	
	public static void logout()
	{
		defaultInstance = new Session();
	}
}