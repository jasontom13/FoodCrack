package edu.arizona.wood.tom;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import edu.arizona.foodcrack.R;

public class MainActivity extends Activity {

	FlyOutContainer root;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);

		this.setContentView(root);
		Button newGameButton = (Button) findViewById(R.id.newGameButton);
		Button achievementsButton = (Button) findViewById(R.id.achievementsButton);
		Button settingsButton = (Button) findViewById(R.id.settingsButton);
		Button logoutButton = (Button) findViewById(R.id.logoutButton);

		newGameButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(animAlpha);
				Intent i = new Intent(MainActivity.this, GameActivity.class);	
				startActivity(i);
			}
		});
		
		achievementsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(animAlpha);
				//Intent i = new Intent(MainActivity.this, AchievementActivity.class);	
			}
		});
		
		settingsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(animAlpha);
				//Intent i = new Intent(MainActivity.this, SettingsActivity.class);	
			}
		});
		
		logoutButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(animAlpha);
				//Intent i = new Intent(MainActivity.this, GameActivity.class);	
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
}
