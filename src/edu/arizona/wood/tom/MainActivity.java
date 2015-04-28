package edu.arizona.wood.tom;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.model.Session;

public class MainActivity extends Activity {

	FlyOutContainer root;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().hide();
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);

		this.setContentView(root);
		Button newGameButton = (Button) findViewById(R.id.newGameButton);
		((TextView) findViewById(R.id.playerName)).setText(Session.getDefaultInstance().getLoggedInUser().getUsername());

		newGameButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha));
				Intent i = new Intent(MainActivity.this, GameActivity.class);	
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
		return true;
	}

	public void toggleMenu(View v) {
		this.root.toggleMenu();
	}
	
	public void logout(View v)
	{
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
		Session.logout();
		
		this.finish();
	}
}
