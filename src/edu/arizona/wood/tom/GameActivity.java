package edu.arizona.wood.tom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.asynctasks.ImageLoadTask;
import edu.arizona.wood.tom.model.Achievements;
import edu.arizona.wood.tom.model.Question;
import edu.arizona.wood.tom.model.Session;
import edu.arizona.wood.tom.model.Statistics;

public class GameActivity extends Activity {
	final int FADEDURATION = 1500;
	final int TIMERLENGTH = 20000;
	boolean removedFirstQuestion = false;
	boolean removedSecondQuestion = false;
	List<Button> buttons;
	ProgressBar progress;
	Question q;
	long timeProgressed;
	TextView response;
	TextView questionInfo;
	Button sel1;
	Button sel2;
	Button sel3;
	Button sel4;
	Button newGameButton;
	Button mainMenuButton;
	CountDownTimer timer;
	ViewSwitcher viewSwitcher;
	Animation slide_in_left, slide_out_right;
	ImageView foodImage;
	ArrayList<String> availableQuestions;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_game);
		// Setup View Switcher
		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewswitcher);
		slide_in_left = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_in_left);
		slide_out_right = AnimationUtils.loadAnimation(this,
				android.R.anim.slide_out_right);

		viewSwitcher.setInAnimation(slide_in_left);
		viewSwitcher.setOutAnimation(slide_out_right);
		
		foodImage = (ImageView) findViewById(R.id.foodImage);
		buttons = new ArrayList<Button>();
		
		response = (TextView) findViewById(R.id.gameResponseText);
		questionInfo = (TextView) findViewById(R.id.questionInfo);
		
		availableQuestions = Session.getDefaultInstance().getAvailableQuestions();

		// Setup Answer Buttons
		sel1 = (Button) findViewById(R.id.selection1);
		sel2 = (Button) findViewById(R.id.selection2);
		sel3 = (Button) findViewById(R.id.selection3);
		sel4 = (Button) findViewById(R.id.selection4);
		
		buttons.add(sel1);
		buttons.add(sel2);
		buttons.add(sel3);
		buttons.add(sel4);
		
		
		
		setupScreen();
		

		
		newGameButton = (Button) findViewById(R.id.newGameButton);
		mainMenuButton = (Button) findViewById(R.id.mainMenuButton);
		
		
		
		
		newGameButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewSwitcher.showNext();
				setupScreen();
			}
			
		});
		
		mainMenuButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GameActivity.this.finish();
			}
			
		});
			
		







	}
	
	private void setupScreen(){
		timer = new myTimer(TIMERLENGTH, 1);
		timer.start();
		response.setVisibility(View.INVISIBLE);
		sel1.setClickable(true);
		sel2.setClickable(true);
		sel3.setClickable(true);
		sel4.setClickable(true);
		sel1.setVisibility(View.VISIBLE);
		sel2.setVisibility(View.VISIBLE);
		sel3.setVisibility(View.VISIBLE);
		sel4.setVisibility(View.VISIBLE);
		sel1.setOnTouchListener(new TouchListener());
		sel2.setOnTouchListener(new TouchListener());
		sel3.setOnTouchListener(new TouchListener());
		sel4.setOnTouchListener(new TouchListener());
		
		if (availableQuestions.size()==0){
			finish();
		}
		
		
		String qid;
		qid = availableQuestions.get(new Random().nextInt(availableQuestions.size()));
//		qid = Session
//				.getDefaultInstance()
//				.getAvailableQuestions()
//				.get(new Random().nextInt(Session.getDefaultInstance()
//						.getAvailableQuestions().size()));
		// qid is now a random question in a list of all available questions,
		// before getting the question need to verify in table that the user has
		// not answered this question yet.
		q = DatabaseHelper.getDefaultInstance().getQuestion(qid);
		
		
		
		// Async task to download question image to imageview
		new ImageLoadTask(q.getImgUrl(), foodImage).execute();


		TextView questionText = (TextView) findViewById(R.id.questionText);
		progress = (ProgressBar) findViewById(R.id.gameTimer);

		// Shuffle the buttons so we can assign answers.


		Collections.shuffle(buttons);
		// Set text fields
		buttons.get(0).setText(q.getCorrectResponse());
		buttons.get(1).setText(q.getResponse1());
		buttons.get(2).setText(q.getResponse2());
		buttons.get(3).setText(q.getResponse3());
		questionText.setText(q.getQuestion());
		String username = q.getCreatedBy();
		if (username==null){
			username = "anonymous";
		}
		String locationCreated = q.getLocationCreated();
		if (locationCreated==null){
			locationCreated="unknown location";
		}
		questionInfo.setText("Created By: " + username + " in: " + locationCreated);
		
	}

	protected void validate(View v) {
		

		final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
		fadeIn.setDuration(FADEDURATION);
		fadeIn.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// root.toggleMenu();
				viewSwitcher.showNext();

			}
		});
		int timeToAnswer = (int) timeProgressed;

//		String userName = Session.getDefaultInstance().getLoggedInUser()
//				.getUsername();
//		Statistics stats;
//		stats = DatabaseHelper.getDefaultInstance().getUserStatistics(userName);
		Statistics stats = Session.getDefaultInstance().getStats();

		// Increment Q's answered && millisToAnswer
		stats.setQuestionsAnswered(stats.getQuestionsAnswered() + 1);
		stats.setTotalMillisToAnswer(stats.getTotalMillisToAnswer()
				+ timeToAnswer);

		// Make buttons unresponsive
		sel1.setOnTouchListener(null);
		sel2.setOnTouchListener(null);
		sel3.setOnTouchListener(null);
		sel4.setOnTouchListener(null);

		// Time Out Case
		if (v == null) {
			response.setTextColor(Color.parseColor("#9255a4"));
			response.setText("Out of time!");

			// Update stats

		}
		// Question Answered
		else {
			Button answer = (Button) v;

			if (answer.getText().toString().equals(q.getCorrectResponse())) {
				response.setTextColor(Color.parseColor("#43c6b9"));
				response.setText("Correct!");
				stats.setCorrectlyAnswered(stats.getCorrectlyAnswered() + 1);
				if (stats.getCurrentStreak() <= 0) {
					stats.setCurrentStreak(1);
				} else {
					stats.setCurrentStreak(stats.getCurrentStreak() + 1);
				}
				if (stats.getCurrentStreak() > stats.getWinningStreak()) {
					stats.setWinningStreak(stats.getCurrentStreak());
				}
			} else {
				response.setTextColor(Color.parseColor("#c6202c"));
				response.setText("Wrong");
				if (stats.getCurrentStreak() <= 0) {
					stats.setCurrentStreak(stats.getCurrentStreak() - 1);
				} else {
					stats.setCurrentStreak(-1);
				}
				if (stats.getCurrentStreak() < stats.getLosingStreak()) {
					stats.setLosingStreak(stats.getCurrentStreak());
				}
			}
		}
		
		updateAchievements();
		
		DatabaseHelper.getDefaultInstance().updateStatistics(stats);
		availableQuestions.remove(q.getQid());
		DatabaseHelper.getDefaultInstance().addAnswered(Session.getDefaultInstance().getLoggedInUser().getUsername(), q.getQid());
		
		response.startAnimation(fadeIn);
		response.setVisibility(View.VISIBLE);

	}

	private boolean isValid(View v, MotionEvent event) {
		if (event.getX() >= 0 && event.getX() <= v.getWidth()
				&& event.getY() >= 0 && event.getY() <= v.getHeight()) {
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {

	}
	
	private class myTimer extends CountDownTimer{

		public myTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		public void onTick(long millisUntilFinished) {
			timeProgressed = TIMERLENGTH - millisUntilFinished;
			progress.setProgress((int) (TIMERLENGTH - millisUntilFinished)
					* progress.getMax() / TIMERLENGTH);
			if (millisUntilFinished < TIMERLENGTH / 2) {
				if (!removedFirstQuestion) {
					removedFirstQuestion = true;
					Animation fadeout1 = AnimationUtils.loadAnimation(
							GameActivity.this, R.anim.anim_fadeout);
					fadeout1.setAnimationListener(new Animation.AnimationListener() {

						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							buttons.get(1).setVisibility(View.INVISIBLE);

						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub

						}

					});
					buttons.get(1).startAnimation(fadeout1);
					buttons.get(1).setClickable(false);
				}
				if (millisUntilFinished < TIMERLENGTH / 4) {
					if (!removedSecondQuestion) {
						removedSecondQuestion = true;
						Animation fadeout2 = AnimationUtils.loadAnimation(
								GameActivity.this, R.anim.anim_fadeout);
						fadeout2.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onAnimationEnd(Animation animation) {
								// TODO Auto-generated method stub
								buttons.get(2)
										.setVisibility(View.INVISIBLE);

							}

							@Override
							public void onAnimationRepeat(
									Animation animation) {
								// TODO Auto-generated method stub

							}

						});
						buttons.get(2).startAnimation(fadeout2);
						buttons.get(2).setClickable(false);
					}
				}
			}

		}

		public void onFinish() {
			GameActivity.this.validate(null);
		}
		
	}
	
	private class TouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				v.setBackgroundResource(R.drawable.button_selector_off);
				break;
			case MotionEvent.ACTION_UP:
				v.setBackgroundResource(R.drawable.button_selector_on);
				if (isValid(v, event)) {
					GameActivity.this.timer.cancel();
					GameActivity.this.validate(v);
				}
				break;
			}

			return false;
		}

	}
	
	private void updateAchievements(){
		Statistics stats = Session.getDefaultInstance().getStats();
		Achievements achievements = Session.getDefaultInstance().getAchievements();
		int correct = stats.getCorrectlyAnswered();
		int streak = stats.getCurrentStreak();
		if (correct==1){
			achievements.setFirstRight(true);
		}
		if (streak==5){
			achievements.setStreakFive(true);
		}
		if (streak==10){
			achievements.setStreakTen(true);
		}
		if (streak==25){
			achievements.setStreakTwentyFive(true);
		}
		if (streak==-5){
			achievements.setStreakLoseFive(true);
		}
		if (correct>=25){
			achievements.setRightTwentyFive(true);
			if(correct>=50){
				achievements.setRightFifty(true);
				if(correct>=100){
					achievements.setRightHundred(true);
				}
			}
		}
		DatabaseHelper.getDefaultInstance().updateAchievements(achievements);
		
		
	}

}
