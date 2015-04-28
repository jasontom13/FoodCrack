package edu.arizona.wood.tom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.asynctasks.ImageLoadTask;
import edu.arizona.wood.tom.model.Question;
import edu.arizona.wood.tom.model.Session;
import edu.arizona.wood.tom.model.Statistics;

public class GameActivity extends Activity{
	final int TIMERLENGTH = 20000;
	boolean removedFirstQuestion=false;
	boolean removedSecondQuestion=false;
	List<Button> buttons = new ArrayList<Button>();
	ProgressBar progress;
	Question q;
	long timer;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		ImageView foodImage = (ImageView) findViewById(R.id.foodImage);
		TextView questionText = (TextView) findViewById(R.id.questionText);
		Button sel1 = (Button) findViewById(R.id.selection1);
		Button sel2 = (Button) findViewById(R.id.selection2);
		Button sel3 = (Button) findViewById(R.id.selection3);
		Button sel4 = (Button) findViewById(R.id.selection4);
		progress = (ProgressBar) findViewById(R.id.gameTimer);
		
		// Shuffle the buttons so we can assign answers.
		
		buttons.add(sel1);
		buttons.add(sel2);
		buttons.add(sel3);
		buttons.add(sel4);
		Collections.shuffle(buttons);
		
		
		/* Need to verify which questions the user has not already answered, but for now, hardcoded question #69*/
		String qid;
		qid = Session.getDefaultInstance().getAvailableQuestions().get(new Random().nextInt(Session.getDefaultInstance().getAvailableQuestions().size()));
		q = DatabaseHelper.getDefaultInstance().getQuestion(qid);
		
		// Set text fields
		buttons.get(0).setText(q.getCorrectResponse());
		buttons.get(1).setText(q.getResponse1());
		buttons.get(2).setText(q.getResponse2());
		buttons.get(3).setText(q.getResponse3());
		questionText.setText(q.getQuestion());

		new CountDownTimer(TIMERLENGTH, 1) {
		     public void onTick(long millisUntilFinished) {
		    	 timer=TIMERLENGTH-millisUntilFinished;
		    	 progress.setProgress((int) (TIMERLENGTH-millisUntilFinished)*progress.getMax()/TIMERLENGTH);
		    	 if (millisUntilFinished<TIMERLENGTH/2){
		    		 if (!removedFirstQuestion){
		    			 removedFirstQuestion=true;
		    			 Animation fadeout1 = AnimationUtils.loadAnimation(GameActivity.this,R.anim.anim_fadeout);
				    	 fadeout1.setAnimationListener(new Animation.AnimationListener(){

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
		    		 if (millisUntilFinished<TIMERLENGTH/4){
		    			 if (!removedSecondQuestion){
		    				 removedSecondQuestion=true;
		    				 Animation fadeout2 = AnimationUtils.loadAnimation(GameActivity.this,R.anim.anim_fadeout);
					    	 fadeout2.setAnimationListener(new Animation.AnimationListener(){

									@Override
									public void onAnimationStart(Animation animation) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void onAnimationEnd(Animation animation) {
										// TODO Auto-generated method stub
										buttons.get(2).setVisibility(View.INVISIBLE);
										
									}

									@Override
									public void onAnimationRepeat(Animation animation) {
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
		    	 
		         
		     }
		  }.start();
		


		
		// Async task to download question image to imageview
		new ImageLoadTask(q.getImgUrl(), foodImage).execute();
		
		
		class TouchListener implements OnTouchListener{

			@Override
			public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		        	v.setBackgroundResource(R.drawable.button_selector_off);
		            break;
		        case MotionEvent.ACTION_UP:
		        	v.setBackgroundResource(R.drawable.button_selector_on);
		        	if (isValid(v,event)){
		        		GameActivity.this.validate(v);
		        	}
		            break;
		        }
		        
		        return false;
		    }
			
		}
		
		sel1.setOnTouchListener(new TouchListener());
		sel2.setOnTouchListener(new TouchListener());
		sel3.setOnTouchListener(new TouchListener());
		sel4.setOnTouchListener(new TouchListener());

		
	
		
//		sel1.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//		        switch(event.getAction()) {
//		        case MotionEvent.ACTION_DOWN:
//		        	v.setBackgroundResource(R.drawable.button_selector_off);
//		            break;
//		        case MotionEvent.ACTION_UP:
//		        	v.setBackgroundResource(R.drawable.button_selector_on);
//		        	if (isValid(v,event)){
//		        		GameActivity.this.validate(v);
//		        	}
//		            break;
//		        }
//		        
//		        return false;
//		    }	
//		});
//
//		sel2.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//		        switch(event.getAction()) {
//		        case MotionEvent.ACTION_DOWN:
//		        	v.setBackgroundResource(R.drawable.button_selector_off);
//		            break;
//		        case MotionEvent.ACTION_UP:
//		        	v.setBackgroundResource(R.drawable.button_selector_on);
//		        	if (isValid(v,event)){
//		        		//FILL
//		        	}
//		            break;
//		        }
//		        
//		        return false;
//		    }	
//		});
//		sel3.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//		        switch(event.getAction()) {
//		        case MotionEvent.ACTION_DOWN:
//		        	v.setBackgroundResource(R.drawable.button_selector_off);
//		            break;
//		        case MotionEvent.ACTION_UP:
//		        	v.setBackgroundResource(R.drawable.button_selector_on);
//		        	if (isValid(v,event)){
//		        		//FILL
//		        	}
//		            break;
//		        }
//		        
//		        return false;
//		    }	
//		});
//		sel4.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//		        switch(event.getAction()) {
//		        case MotionEvent.ACTION_DOWN:
//		        	v.setBackgroundResource(R.drawable.button_selector_off);
//		            break;
//		        case MotionEvent.ACTION_UP:
//		        	v.setBackgroundResource(R.drawable.button_selector_on);
//		        	if (isValid(v,event)){
//		        		//FILL
//		        	}
//		            break;
//		        }
//		        
//		        return false;
//		    }	
//		});
//		sel1.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.anim_alpha));
//				
//			}
//		});
//		sel2.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.anim_alpha));
//			}
//		});
//		sel3.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.anim_alpha));
//			}
//		});
//		sel4.setOnClickListener(new OnClickListener(){
//			@Override
//			public void onClick(View v) {
//				v.startAnimation(AnimationUtils.loadAnimation(GameActivity.this,R.anim.anim_alpha));
//			}
//		});
		
		//DatabaseHelper.getDefaultInstance().getQuestion(questionId);
	}
	
	protected void validate(View v) {
		Button answer = (Button) v;
		int timeToAnswer = (int)timer;
		TextView response = (TextView) findViewById(R.id.gameResponseText);
		final Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
	    fadeIn.setDuration(3000);
		if (answer.getText().toString().equals(q.getCorrectResponse())){
			response.setText(":)");
		}
		else{
			response.setText(":(");
		}
		String userName = Session.getDefaultInstance().getLoggedInUser().getUsername();
		Statistics stats;
		stats = DatabaseHelper.getDefaultInstance().getUserStatistics(userName);
		//stats.set
		response.startAnimation(fadeIn);
		response.setVisibility(View.VISIBLE);
	}
	

	private boolean isValid(View v, MotionEvent event){
		if (event.getX() >= 0 && event.getX() <= v.getWidth() &&
        	event.getY() >= 0 && event.getY() <= v.getHeight()){
        		return true;
        	}
		return false;
	}

	@Override
	public void onBackPressed() {
		
	}
	
}
