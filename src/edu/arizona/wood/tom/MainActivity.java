package edu.arizona.wood.tom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.model.Session;
import edu.arizona.wood.tom.model.Statistics;

public class MainActivity extends Activity {

	FlyOutContainer root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// getActionBar().hide();
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);
		
		this.setContentView(root);
		
		Button newGameButton = (Button) findViewById(R.id.newGameButton);
		((TextView) findViewById(R.id.playerName)).setText(Session
				.getDefaultInstance().getLoggedInUser().getUsername());
		//updateStats();

		newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				v.startAnimation(AnimationUtils.loadAnimation(
						MainActivity.this, R.anim.anim_alpha));
				Intent i = new Intent(MainActivity.this, GameActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		updateStats();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
		return true;
	}

	public void updateStats() {
		Log.d("DDB", "Call");
		Statistics stats = Session.getDefaultInstance().getStats();
		if (stats != null) {
			((TextView) findViewById(R.id.questionsAnswered)).setText(stats
					.getQuestionsAnswered()+"");
			((TextView) findViewById(R.id.correctlyAnswered)).setText(stats
					.getCorrectlyAnswered()+"");
			((TextView) findViewById(R.id.currentStreak)).setText(stats
					.getCurrentStreak()+"");
			((TextView) findViewById(R.id.bestWinning)).setText(stats
					.getWinningStreak()+"");
			((TextView) findViewById(R.id.bestLosing)).setText(stats
					.getLosingStreak()+"");
			((TextView) findViewById(R.id.questionsCreated)).setText(stats
					.getQuestionsCreated()+"");
			if (stats.getQuestionsAnswered() != 0) {
				((TextView) findViewById(R.id.averageTime)).setText((stats
						.getTotalMillisToAnswer()
						/ stats.getQuestionsAnswered())+"");
			} else {
				((TextView) findViewById(R.id.averageTime)).setText("N/A");
			}
		}
	}

	public void toggleMenu(View v) {
		this.root.toggleMenu();
	}

	public void logout(View v) {
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
		Session.logout();

		this.finish();
	}

	public void addQuestion(View v) {
		Intent i = new Intent(this, QuestionFactoryActivity.class);
		startActivity(i);
	}
}
