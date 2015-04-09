package edu.arizona.wood.tom;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import edu.arizona.foodcrack.R;

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
		
		
		//DatabaseHelper.getDefaultInstance().getQuestion(questionId);
	}
	
}
