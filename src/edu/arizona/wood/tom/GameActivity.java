package edu.arizona.wood.tom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.asynctasks.ImageLoadTask;
import edu.arizona.wood.tom.model.Question;

public class GameActivity extends Activity{
	
	
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
		
		// Shuffle the buttons so we can assign answers.
		List<Button> buttons = new ArrayList<Button>();
		buttons.add(sel1);
		buttons.add(sel2);
		buttons.add(sel3);
		buttons.add(sel4);
		Collections.shuffle(buttons);
		
		/* Need to verify which questions the user has not already answered, but for now, hardcoded question #69*/
		String qid;
		qid="69";
		Question q = DatabaseHelper.getDefaultInstance().testGetQuestion(qid);
		
		// Async task to download question image to imageview
		new ImageLoadTask(q.getImgUrl(), foodImage).execute();
		
		// Set text fields
		buttons.get(0).setText(q.getCorrectResponse());
		buttons.get(1).setText(q.getResponse1());
		buttons.get(2).setText(q.getResponse2());
		buttons.get(3).setText(q.getResponse3());
		questionText.setText(q.getQuestion());
		
		sel1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		        	v.setBackgroundResource(R.drawable.button_selector_off);
		            break;
		        case MotionEvent.ACTION_UP:
		        	v.setBackgroundResource(R.drawable.button_selector_on);
		        	if (isValid(v,event)){
		        		//FILL
		        	}
		            break;
		        }
		        
		        return false;
		    }	
		});

		sel2.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		        	v.setBackgroundResource(R.drawable.button_selector_off);
		            break;
		        case MotionEvent.ACTION_UP:
		        	v.setBackgroundResource(R.drawable.button_selector_on);
		        	if (isValid(v,event)){
		        		//FILL
		        	}
		            break;
		        }
		        
		        return false;
		    }	
		});
		sel3.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		        	v.setBackgroundResource(R.drawable.button_selector_off);
		            break;
		        case MotionEvent.ACTION_UP:
		        	v.setBackgroundResource(R.drawable.button_selector_on);
		        	if (isValid(v,event)){
		        		//FILL
		        	}
		            break;
		        }
		        
		        return false;
		    }	
		});
		sel4.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
		        switch(event.getAction()) {
		        case MotionEvent.ACTION_DOWN:
		        	v.setBackgroundResource(R.drawable.button_selector_off);
		            break;
		        case MotionEvent.ACTION_UP:
		        	v.setBackgroundResource(R.drawable.button_selector_on);
		        	if (isValid(v,event)){
		        		//FILL
		        	}
		            break;
		        }
		        
		        return false;
		    }	
		});
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
	
	private boolean isValid(View v, MotionEvent event){
		if (event.getX() >= 0 && event.getX() <= v.getWidth() &&
        	event.getY() >= 0 && event.getY() <= v.getHeight()){
        		return true;
        	}
		return false;
	}
	
}
