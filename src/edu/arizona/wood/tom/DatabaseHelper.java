package edu.arizona.wood.tom;

import java.util.ArrayList;

import android.util.Log;

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
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import edu.arizona.wood.tom.model.Question;
import edu.arizona.wood.tom.model.Statistics;
import edu.arizona.wood.tom.model.User;
import edu.arizona.wood.tom.model.UserResponse;

public class DatabaseHelper {
	private static DatabaseHelper defaultInstance;
	private static final String ACCESS_KEY = "AKIAI4CSISLJMKU3DWVQ";
	private static final String SECRET_KEY = "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";
	private static final String TAG = "DDB";

    static String replyTableName = "Reply";
    

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
	
	private void createTable(
	        String tableName, long readCapacityUnits, long writeCapacityUnits, 
	        String hashKeyName, String hashKeyType, 
	        String rangeKeyName, String rangeKeyType) {

	        try {

	            ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
	            keySchema.add(new KeySchemaElement()
	                .withAttributeName(hashKeyName)
	                .withKeyType(KeyType.HASH));
	            
	            ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
	            attributeDefinitions.add(new AttributeDefinition()
	                .withAttributeName(hashKeyName)
	                .withAttributeType(hashKeyType));

	            if (rangeKeyName != null) {
	                keySchema.add(new KeySchemaElement()
	                    .withAttributeName(rangeKeyName)
	                    .withKeyType(KeyType.RANGE));
	                attributeDefinitions.add(new AttributeDefinition()
	                    .withAttributeName(rangeKeyName)
	                    .withAttributeType(rangeKeyType));
	            }

	            CreateTableRequest request = new CreateTableRequest()
	                    .withTableName(tableName)
	                    .withKeySchema(keySchema)
	                    .withProvisionedThroughput( new ProvisionedThroughput()
	                        .withReadCapacityUnits(readCapacityUnits)
	                        .withWriteCapacityUnits(writeCapacityUnits));

	            // If this is the Reply table, define a local secondary index
	            if (replyTableName.equals(tableName)) {
	                
	                attributeDefinitions.add(new AttributeDefinition()
	                    .withAttributeName("PostedBy")
	                    .withAttributeType("S"));

	                ArrayList<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<LocalSecondaryIndex>();
	                localSecondaryIndexes.add(new LocalSecondaryIndex()
	                    .withIndexName("PostedBy-Index")
	                    .withKeySchema(
	                        new KeySchemaElement().withAttributeName(hashKeyName).withKeyType(KeyType.HASH), 
	                        new KeySchemaElement() .withAttributeName("PostedBy") .withKeyType(KeyType.RANGE))
	                    .withProjection(new Projection() .withProjectionType(ProjectionType.KEYS_ONLY)));

	                request.setLocalSecondaryIndexes(localSecondaryIndexes);
	            }

	            request.setAttributeDefinitions(attributeDefinitions);

	            System.out.println("Issuing CreateTable request for " + tableName);
	            CreateTableResult table = db.createTable(request);
	            System.out.println("Waiting for " + tableName
	                + " to be created...this may take a while...");

	        } catch (Exception e) {
	            System.err.println("CreateTable request failed for " + tableName);
	            System.err.println(e.getMessage());
	        }
	    }

	public static DatabaseHelper getDefaultInstance() {
		if (defaultInstance == null) {
			defaultInstance = new DatabaseHelper();
		}
		
//		Question q = new Question();
//		q.setCorrectResponse("Sup");
//		q.setCreatedBy("Jason");
//		q.setDateCreated("Today dummy");
//		q.setImgUrl("www.nope.com");
//		q.setLocationCreated("Earth");
//		q.setQuestion("5 stars");
//		q.setResponse1("no");
//		q.setResponse2("no");
//		q.setResponse3("no");
//		
//		try {
//			Log.d(TAG, "Creating question");
//			defaultInstance.mapper.save(q);
//			Log.d(TAG, "Created");
//		} catch (Exception e)
//		{
//			Log.e(TAG, "error", e);
//		}

		return defaultInstance;
	}

	public UserResponse addUser(String username, String hash) {
		// Check to see if already exists in database
		Log.d(TAG, "Query active...");
		try
		{
			User user = mapper.load(User.class, username);
			if (user != null)
			{
				return UserResponse.EXISTS;
			}
		} catch (Exception e) {}
		
		// Build user to add
		User user = new User();
		user.setUsername(username);
		user.setHashword(hash);
		
		// Add user to DB
		try
		{
			mapper.save(user);
			return UserResponse.SUCCESS;
		} catch (Exception e)
		{
			return UserResponse.FAILURE;
		}
	}

	public User getUser(String username, String hash) {
		Log.d(TAG, "Query active...");
		try
		{
			User user = mapper.load(User.class, username, hash);
			Log.d(TAG, user.getUsername() + " " + user.getHashword());
			return user;
		} catch (Exception e)
		{
			Log.d(TAG, "No user found");
			return null;
		}
	}

	public boolean UpdateUserStats(String username, Statistics stats) {
		return true;
	}

	public boolean addQuestion() {
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