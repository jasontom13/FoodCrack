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

		return defaultInstance;
	}

	/**
	 * Adds a user to the Database
	 * @param username
	 * 	Username of the user
	 * @param hash
	 * 	Hash of their password using SHA-256
	 * @return
	 *	Returns a UserResponse saying if the user was added or not or already exists
	 */
	public UserResponse addUser(String username, String hash) {
		// Check to see if already exists in database
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

	/**
	 * Gets a user from the Database
	 * @param username 
	 *	Username of the user
	 * @param hash
	 *	Hash of their password using SHA-256
	 * @return
	 *	Returns a User object received from the Database
	 */
	public User getUser(String username, String hash) {
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

	/**
	 * Not yet implemented
	 * @param username
	 * @param stats
	 * @return
	 */
	public boolean UpdateUserStats(String username, Statistics stats) {
		return true;
	}

	/**
	 * Adds a question to the Database
	 * @param question
	 *	Question object to add
	 * @return
	 *	true if successfully added to the Database
	 */
	public boolean addQuestion(Question question) {
		// Add user to DB
		try
		{
			mapper.save(question);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * Gets a question from the Database
	 * @param questionId
	 *	id of the question to retrieve
	 * @return
	 *	Returns a Question object retrieved from the Database
	 *	Returns null if no question matches the id
	 */
	public Question getQuestion(String questionId) {
		try
		{
			Question q = mapper.load(Question.class, questionId);
			return q;
		} catch (Exception e)
		{
			return null;
		}
	}
	
	public ArrayList<String> getAllQuestionIds()
	{
		ArrayList<String> questionIds = new ArrayList<String>();
		
//		mapper.load
		
		return questionIds;
	}
}