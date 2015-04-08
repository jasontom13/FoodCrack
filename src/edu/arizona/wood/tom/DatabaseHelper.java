package edu.arizona.wood.tom;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class DatabaseHelper {
	private static DatabaseHelper defaultInstance;
	private static final String ACCESS_KEY = "AKIAI4CSISLJMKU3DWVQ";
	private static final String SECRET_KEY = "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";

	private AmazonDynamoDBClient db;

	private DatabaseHelper() throws Exception {
		String result = init();
		if (!result.equals("")) {
			throw new Exception("Bad connection to AWS: " + result);
		}
	}

	private String init() {
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY,
				SECRET_KEY);
		db = new AmazonDynamoDBClient(credentials);

		return ""; // Empty string if successful connection
	}

	public static DatabaseHelper getDefaultInstance() throws Exception {
		if (defaultInstance == null) {
			defaultInstance = new DatabaseHelper();
		}

		return defaultInstance;
	}

	public UserResponse addUser(String username, String hash) {

		return UserResponse.SUCCESS; // Only return true if successful
	}

	public UserInfo getUserInfo(String username, String hash) {
		UserInfo info = null;

		return info;
	}

	public boolean UpdateUserStats(String username, String hash,
			Statistics stats) {
		return true;
	}

	public boolean AddQuestion() {
		return true;
	}

	public Question getQuestion(int questionId) {
		Question question = null;

		return question;
	}

	public int getNumQuestions() {
		return 0;
	}

	public boolean removeQuestions(int questionId) {
		return true;
	}
}