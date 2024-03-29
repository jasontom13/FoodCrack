package edu.arizona.wood.tom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import edu.arizona.wood.tom.model.Achievements;
import edu.arizona.wood.tom.model.Answered;
import edu.arizona.wood.tom.model.Question;
import edu.arizona.wood.tom.model.Statistics;
import edu.arizona.wood.tom.model.User;
import edu.arizona.wood.tom.model.UserResponse;

public class DatabaseHelper {
	private static DatabaseHelper defaultInstance;
	private static final String ACCESS_KEY = "AKIAI4CSISLJMKU3DWVQ";
	private static final String SECRET_KEY = "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";
	private static final String TAG = "DDB";

	private static final String QUESTION_TABLE = "Questions";
	private static final String USER_TABLE = "Users";
	private static final String ACHIEVEMENTS_TABLE = "Achievements";
	private static final String ANSWERED_LIST_TABLE = "AnsweredList";
	private static final String STATISTICS_TABLE = "Statistics";

	static String replyTableName = "Reply";

	private AmazonDynamoDBClient db;

	private DynamoDBMapper mapper;

	private DatabaseHelper() {
		String result = init();
		if (!result.equals("")) {
			// throw new Exception("Bad connection to AWS: " + result);
		}
	}

	private String init() {
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY,
				SECRET_KEY);
		db = new AmazonDynamoDBClient(credentials);
		db.setRegion(Region.getRegion(Regions.US_WEST_2));
		mapper = new DynamoDBMapper(db);

		return ""; // Empty string if successful connection
	}


	public static DatabaseHelper getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new DatabaseHelper();
		}

		return defaultInstance;
	}

	/**
	 * Adds a user to the Database
	 * 
	 * @param username
	 *            Username of the user
	 * @param hash
	 *            Hash of their password using SHA-256
	 * @return Returns a UserResponse saying if the user was added or not or
	 *         already exists
	 */
	public UserResponse addUser(String username, String hash) {
		// Check to see if already exists in database
		try {
			User user = mapper.load(User.class, username);
			if (user != null) {
				return UserResponse.EXISTS;
			}
		} catch (Exception e) {
		}

		// Build user to add
		User user = new User();
		user.setUsername(username);
		user.setHashword(hash);

		// Build users stats
		Statistics stats = new Statistics();
		stats.setUsername(username);

		// Build users achievements
		Achievements ach = new Achievements();
		ach.setUsername(username);

		// Add user to DB
		try {
			mapper.save(user);
			mapper.save(stats);
			mapper.save(ach);
			return UserResponse.SUCCESS;
		} catch (Exception e) {
			return UserResponse.FAILURE;
		}
	}

	/**
	 * Gets a user from the Database
	 * 
	 * @param username
	 *            Username of the user
	 * @param hash
	 *            Hash of their password using SHA-256
	 * @return Returns a User object received from the Database
	 */
	public User getUser(String username, String hash) {
		try {
			User user = mapper.load(User.class, username, hash);
			return user;
		} catch (Exception e) {
			Log.d(TAG, "No user found");
			return null;
		}
	}

	/**
	 * Not yet implemented
	 * 
	 * @param username
	 * @param stats
	 * @return
	 */
	public boolean UpdateUserStats(String username, Statistics stats) {
		try {
			mapper.save(stats);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Adds a question to the Database
	 * 
	 * @param question
	 *            Question object to add
	 * @return true if successfully added to the Database
	 */
	public boolean addQuestion(Question question) {
		// Add user to DB
		try {
			mapper.save(question);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Gets a question from the Database
	 * 
	 * @param questionId
	 *            id of the question to retrieve
	 * @return Returns a Question object retrieved from the Database Returns
	 *         null if no question matches the id
	 */
	public Question getQuestion(String questionId) {
		try {
			Question q = mapper.load(Question.class, questionId);
			return q;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets all question id's from the database
	 * 
	 * @return ArrayList of Strings of all question id's
	 */
	public ArrayList<String> getAllQuestionIds() {
		ArrayList<String> questionIds = new ArrayList<String>();

		ScanResult result = null;
		do {
			ScanRequest req = new ScanRequest();
			req.setTableName(QUESTION_TABLE);

			if (result != null) {
				req.setExclusiveStartKey(result.getLastEvaluatedKey());
			}

			result = db.scan(req);

			List<Map<String, AttributeValue>> rows = result.getItems();

			for (Map<String, AttributeValue> map : rows) {
				AttributeValue v = map.get("qid");
				String id = v.getS();
				questionIds.add(id);
			}
		} while (result.getLastEvaluatedKey() != null);

		return questionIds;
	}

	public Achievements getUserAchievements(String username) {
		try {
			Achievements ach = mapper.load(Achievements.class, username);
			return ach;
		} catch (Exception e) {
			return null;
		}
	}

	public void updateAchievements(Achievements ach) {
		mapper.save(ach);
	}

	public Statistics getUserStatistics(String username) {
		return mapper.load(Statistics.class, username);
	}

	public void updateStatistics(Statistics stats) {
		mapper.save(stats);
	}

	public List<String> getAnswered(String username) {
		ArrayList<String> answeredIds = new ArrayList<String>();

		ScanResult result = null;
		do {
			ScanRequest req = new ScanRequest();
			req.setTableName(ANSWERED_LIST_TABLE);

			if (result != null) {
				req.setExclusiveStartKey(result.getLastEvaluatedKey());
			}

			result = db.scan(req);

			List<Map<String, AttributeValue>> rows = result.getItems();

			for (Map<String, AttributeValue> map : rows) {
				AttributeValue qid = map.get("qid");
				AttributeValue user = map.get("username");
				if (user != null && user.getS().equals(username)) {
					answeredIds.add(qid.getS());
				}
			}
		} while (result.getLastEvaluatedKey() != null);

		return answeredIds;
	}

	public void addAnswered(String username, String qid) {
		Answered ans = new Answered();
		ans.setUsername(username);
		ans.setQid(qid);

		mapper.save(ans);
	}
}