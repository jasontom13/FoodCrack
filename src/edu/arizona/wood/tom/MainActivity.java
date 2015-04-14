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
import edu.arizona.wood.tom.model.Question;

public class MainActivity extends Activity {

	FlyOutContainer root;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha));
				Question q = new Question();
				q.setCorrectResponse("hi");
				q.setImgUrl("http://images.huffingtonpost.com/2014-03-04-40_BigMac.jpg");
				q.setQuestion("What The Eff?");
				q.setQid("69");
				q.setCorrectResponse("big mac");
				q.setResponse1("whopper1");
				q.setResponse2("whopper2");
				q.setResponse3("whopper3");
				DatabaseHelper.getDefaultInstance().testAddQuestion(q);
				Intent i = new Intent(MainActivity.this, GameActivity.class);	
				startActivity(i);
			}
		});
		
		achievementsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha));
				//Intent i = new Intent(MainActivity.this, AchievementActivity.class);	
			}
		});
		
		settingsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha));
				//Intent i = new Intent(MainActivity.this, SettingsActivity.class);	
			}
		});
		
		logoutButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				v.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.anim_alpha));
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
