package edu.arizona.wood.tom;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import edu.arizona.foodcrack.R;

public class LoginActivity extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button login = (Button) findViewById(R.id.loginButton);
		DatabaseHelper dh = DatabaseHelper.getDefaultInstance();
		EditText username = (EditText) findViewById(R.id.nameField);
		EditText pass = (EditText) findViewById(R.id.passField);
		
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//Add to database when methods are implemented
				//username.getText().toString();
				//pass.getText().toString();
				Intent i = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(i);
				
			}
			
		});
	}

}
