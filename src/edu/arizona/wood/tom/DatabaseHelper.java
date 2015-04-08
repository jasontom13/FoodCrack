package edu.arizona.wood.tom;

import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import edu.arizona.wood.tom.Model.Statistics;
import edu.arizona.wood.tom.Model.User;

public class DatabaseHelper {
	private static DatabaseHelper defaultInstance;
	private static final String ACCESS_KEY = "AKIAI4CSISLJMKU3DWVQ";
	private static final String SECRET_KEY = "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";
	private static final String TAG = "DDB";

	private AmazonDynamoDBClient db;
	
	private DynamoDBMapper mapper;

	private DatabaseHelper() {
		String result = init();
		if (!result.equals("")) {
			//throw new Exception("Bad connection to AWS: " + result);
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
	
	private void debugCreate()
	{

        Log.d(TAG, "Create table called");

        KeySchemaElement kse = new KeySchemaElement().withAttributeName(
                "userNo").withKeyType(KeyType.HASH);
        AttributeDefinition ad = new AttributeDefinition().withAttributeName(
                "userNo").withAttributeType(ScalarAttributeType.N);
        ProvisionedThroughput pt = new ProvisionedThroughput()
                .withReadCapacityUnits(10l).withWriteCapacityUnits(5l);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName("Test")
                .withKeySchema(kse).withAttributeDefinitions(ad)
                .withProvisionedThroughput(pt);

        try {
            Log.d(TAG, "Sending Create table request");
            CreateTableResult ctr = db.createTable(request);
            Log.d(TAG, "Create request response successfully recieved" + ctr.toString());
        } catch (AmazonServiceException ex) {
            Log.e(TAG, "Error sending create table request", ex);
        }
	}

	public static DatabaseHelper getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new DatabaseHelper();
		}
		
		//defaultInstance.debugCreate();
		defaultInstance.debugCreate();

		return defaultInstance;
	}

	public UserResponse addUser(String username, String hash) {
		Log.i("DatabaseHelper: ", "Before add...");
		User user = new User(username, hash);
		mapper.save(user);
		Log.i("DatabaseHelper: ", "Adding User...");
		return UserResponse.SUCCESS; // Only return true if successful
	}

	public User getUserInfo(String username, String hash) {
		User info = null;

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