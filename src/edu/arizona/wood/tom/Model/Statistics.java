package edu.arizona.wood.tom.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "Statistics")
public class Statistics {
	// Stats are for this user exclusively
	private String username;
	private int questionsAnswered = 0;
	private int correctlyAnswered = 0;
	private String averageTimeToAnswer = "";
	private int winningStreak = 0;
	private int losingStreak = 0;
	private int currentStreak = 0;
	private boolean currentStreakWinning = true;

	@DynamoDBHashKey(attributeName = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@DynamoDBAttribute(attributeName = "questionsAnswered")
	public int getQuestionsAnswered() {
		return questionsAnswered;
	}

	public void setQuestionsAnswered(int questionsAnswered) {
		this.questionsAnswered = questionsAnswered;
	}

	@DynamoDBAttribute(attributeName = "correctlyAnswered")
	public int getCorrectlyAnswered() {
		return correctlyAnswered;
	}

	public void setCorrectlyAnswered(int correctlyAnswered) {
		this.correctlyAnswered = correctlyAnswered;
	}

	@DynamoDBAttribute(attributeName = "averageTimeToAnswer")
	public String getAverageTimeToAnswer() {
		return averageTimeToAnswer;
	}

	public void setAverageTimeToAnswer(String averageTimeToAnswer) {
		this.averageTimeToAnswer = averageTimeToAnswer;
	}

	@DynamoDBAttribute(attributeName = "winningStreak")
	public int getWinningStreak() {
		return winningStreak;
	}

	public void setWinningStreak(int winningStreak) {
		this.winningStreak = winningStreak;
	}

	@DynamoDBAttribute(attributeName = "losingStreak")
	public int getLosingStreak() {
		return losingStreak;
	}

	public void setLosingStreak(int losingStreak) {
		this.losingStreak = losingStreak;
	}

	@DynamoDBAttribute(attributeName = "currentStreak")
	public int getCurrentStreak() {
		return currentStreak;
	}

	public void setCurrentStreak(int currentStreak) {
		this.currentStreak = currentStreak;
	}

	@DynamoDBAttribute(attributeName = "isCurrentStreakWinning")
	public boolean isCurrentStreakWinning() {
		return currentStreakWinning;
	}

	public void setCurrentStreakWinning(boolean currentStreakWinning) {
		this.currentStreakWinning = currentStreakWinning;
	}
}