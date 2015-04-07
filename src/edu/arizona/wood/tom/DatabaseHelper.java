package edu.arizona.wood.tom;

public class DatabaseHelper {
	private static DatabaseHelper defaultInstance;
	
	private DatabaseHelper()
	{
		init();
	}
	
	private void init()
	{
		
	}
	
	public static DatabaseHelper getDefaultInstance()
	{
		if (defaultInstance == null)
		{
			defaultInstance = new DatabaseHelper();
		}
		
		return defaultInstance;
	}
}