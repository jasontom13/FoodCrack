package edu.arizona.wood.tom.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "Users")
public class User {
	private String username;
	private String hashword;

	@DynamoDBHashKey(attributeName = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@DynamoDBRangeKey(attributeName = "hashword")
	public String getHashword() {
		return hashword;
	}

	public void setHashword(String hashword) {
		this.hashword = hashword;
	}
}
