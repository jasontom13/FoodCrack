package edu.arizona.wood.tom;

import edu.arizona.wood.tom.FlyOutContainer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import edu.arizona.foodcrack.R;

public class MainActivity extends Activity {

	FlyOutContainer root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);

		this.setContentView(root);

		try {
			DatabaseHelper.getDefaultInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
