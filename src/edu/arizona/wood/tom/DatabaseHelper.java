package edu.arizona.wood.tom;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

public class DatabaseHelper {
	private static DatabaseHelper defaultInstance;
	private static final String ACCESS_KEY = "AKIAI4CSISLJMKU3DWVQ";
	private static final String SECRET_KEY = "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";
	
	private DatabaseHelper()
	{
		init();
	}
	
	private void init()
	{
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
	}
	
	public static DatabaseHelper getDefaultInstance()
	{
		if (defaultInstance == null)
		{
			defaultInstance = new DatabaseHelper();
		}
		
		return defaultInstance;
	}
	
	public UserResponse addUser(String username, String hash)
	{
		
		return UserResponse.SUCCESS; // Only return true if successful
	}
	
	public UserInfo getUserInfo(String username, String hash)
	{
		UserInfo info = null;
		
		return info;
	}
	
	public boolean UpdateUserStats(String username, String hash, Statistics stats)
	{
		return true;
	}
	
	public boolean AddQuestion()
	{
		return true;
	}
	
	public Question getQuestion(int questionId)
	{
		Question question = null;
		
		return question;
	}
	
	public int getNumQuestions()
	{
		return 0;
	}
	
	public boolean removeQuestions(int questionId)
	{
		return true;
	}
}