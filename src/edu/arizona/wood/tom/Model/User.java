package edu.arizona.wood.tom.Model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "Users")
public class User {
	private String username;
	private String hashword;
	
	public User(String username, String hashword)
	{
		this.username = username;
		this.hashword = hashword;
	}

	@DynamoDBIndexRangeKey(attributeName = "User")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@DynamoDBHashKey(attributeName = "hashword")
	public String getHashword() {
		return hashword;
	}

	public void setHashword(String hashword) {
		this.hashword = hashword;
	}
}
