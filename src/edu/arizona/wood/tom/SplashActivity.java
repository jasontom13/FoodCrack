package edu.arizona.wood.tom;

import android.app.Activity;
import android.os.Bundle;

public class SplashActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		DatabaseHelper.getDefaultInstance();
	}
}
