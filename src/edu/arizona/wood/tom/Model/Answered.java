package edu.arizona.wood.tom.model;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "AnsweredList")
public class Answered {
	private String username;
	private String qid;

	@DynamoDBHashKey(attributeName = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@DynamoDBRangeKey(attributeName = "qid")
	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}
}