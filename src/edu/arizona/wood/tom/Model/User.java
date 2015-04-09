package edu.arizona.wood.tom.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "Users")
public class User {
	private String username;
	private String hashword;
	private String id;

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

//	@DynamoDBAttribute(attributeName = "id")
//	@DynamoDBAutoGeneratedKey
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
}
