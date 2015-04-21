package edu.arizona.wood.tom;

import java.security.MessageDigest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.model.Session;
import edu.arizona.wood.tom.model.User;
import edu.arizona.wood.tom.model.UserResponse;

public class LoginActivity extends Activity {
	EditText username;
	EditText pass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Button login = (Button) findViewById(R.id.loginButton);
		Button register = (Button) findViewById(R.id.registerButton);
		username = (EditText) findViewById(R.id.nameField);
		pass = (EditText) findViewById(R.id.passField);

		try {
			DatabaseHelper.getDefaultInstance();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Log.e("DDB", "Derp", e1);
		}
		
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = username.getText().toString();
				try {
					MessageDigest md = MessageDigest.getInstance("SHA");
					md.update(pass.getText().toString().getBytes("UTF-8"));
					byte[] digest = md.digest();
					String str = new String(digest);
					
					// TODO: Debugging purposes for blank username
					if (name.equals(""))
					{
						name = "Jason";
						str = "Tom";
					}
					DatabaseHelper dh = DatabaseHelper.getDefaultInstance();
					User user = dh.getUser(name, str);
					if (user != null)
					{
						Session.getDefaultInstance().setLoggedInUser(user);
						
						Session.getDefaultInstance().setAvailableQuestions(DatabaseHelper.getDefaultInstance().getAllQuestionIds());
						
						// Yay, go to main activity, set global user
						Intent i = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(i);
						
						LoginActivity.this.finish();
					}
					else
					{
						// Could not find user in db
						Toast.makeText(getApplicationContext(), "Could not authenticate credentials", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {

				}
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Add to database when methods are implemented
				String name = username.getText().toString();
				try {
					MessageDigest md = MessageDigest.getInstance("SHA");
					md.update(pass.getText().toString().getBytes("UTF-8"));
					byte[] digest = md.digest();
					String str = new String(digest);
					
					// TODO: Debugging purposes for blank username
					if (name.equals(""))
					{
						name = "Jason";
						str = "Tom";
					}
					DatabaseHelper dh = DatabaseHelper.getDefaultInstance();
					UserResponse response = dh.addUser(name, str);
					if (response == UserResponse.SUCCESS)
					{
						// Yay temp toast for success
						Toast.makeText(getApplicationContext(), "Successfully created user: " + name, Toast.LENGTH_SHORT).show();

						Session.getDefaultInstance().setAvailableQuestions(DatabaseHelper.getDefaultInstance().getAllQuestionIds());
						
						Intent i = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(i);
						
						LoginActivity.this.finish();
					}
					else if (response == UserResponse.FAILURE)
					{
						// Could not create
						Toast.makeText(getApplicationContext(), "Could not create user: " + name, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {

				}
			}
		});
	}
}