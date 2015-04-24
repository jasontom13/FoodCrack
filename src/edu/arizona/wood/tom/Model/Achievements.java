package edu.arizona.wood.tom.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "Achievements")
public class Achievements {
	private String username;
	private boolean streakFive = false;
	private boolean streakTen = false;
	private boolean streakTwentyFive = false;
	private boolean streakLoseFive = false;
	private boolean firstRight = false;
	private boolean rightTwentyFive = false;
	private boolean rightFifty = false;
	private boolean rightHundred = false;

	@DynamoDBHashKey(attributeName = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@DynamoDBAttribute(attributeName = "streakFive")
	public boolean isStreakFive() {
		return streakFive;
	}

	public void setStreakFive(boolean streakFive) {
		this.streakFive = streakFive;
	}

	@DynamoDBAttribute(attributeName = "streakTen")
	public boolean isStreakTen() {
		return streakTen;
	}

	public void setStreakTen(boolean streakTen) {
		this.streakTen = streakTen;
	}

	@DynamoDBAttribute(attributeName = "streakTwentyFive")
	public boolean isStreakTwentyFive() {
		return streakTwentyFive;
	}

	public void setStreakTwentyFive(boolean streakTwentyFive) {
		this.streakTwentyFive = streakTwentyFive;
	}

	@DynamoDBAttribute(attributeName = "streakLoseFive")
	public boolean isStreakLoseFive() {
		return streakLoseFive;
	}

	public void setStreakLoseFive(boolean streakLoseFive) {
		this.streakLoseFive = streakLoseFive;
	}

	@DynamoDBAttribute(attributeName = "firstRight")
	public boolean isFirstRight() {
		return firstRight;
	}

	public void setFirstRight(boolean firstRight) {
		this.firstRight = firstRight;
	}

	@DynamoDBAttribute(attributeName = "rightTwentyFive")
	public boolean isRightTwentyFive() {
		return rightTwentyFive;
	}

	public void setRightTwentyFive(boolean rightTwentyFive) {
		this.rightTwentyFive = rightTwentyFive;
	}

	@DynamoDBAttribute(attributeName = "rightFifty")
	public boolean isRightFifty() {
		return rightFifty;
	}

	public void setRightFifty(boolean rightFifty) {
		this.rightFifty = rightFifty;
	}

	@DynamoDBAttribute(attributeName = "rightHundred")
	public boolean isRightHundred() {
		return rightHundred;
	}

	public void setRightHundred(boolean rightHundred) {
		this.rightHundred = rightHundred;
	}
}