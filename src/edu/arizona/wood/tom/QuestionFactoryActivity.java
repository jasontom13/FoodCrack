package edu.arizona.wood.tom;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import edu.arizona.foodcrack.R;
import edu.arizona.wood.tom.model.Question;
import edu.arizona.wood.tom.model.Session;
import edu.arizona.wood.tom.model.Statistics;
import edu.arizona.wood.tom.model.User;

public class QuestionFactoryActivity extends Activity {
	private LocationListener mLocationListener;
	final long LOCATION_REFRESH_TIME = 2000;
	final float LOCATION_REFRESH_DISTANCE = 10;
	EditText question, correct, wrong1, wrong2, wrong3;
	WebView wv;
	String url;
	LocationManager mLocationManager;
	Location currentLocation;
	boolean validUrl=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionfactory);
		mLocationListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				currentLocation = location;
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
	            LOCATION_REFRESH_DISTANCE, mLocationListener);
		
//		url = (EditText) findViewById(R.id.urlEditText);
		question = (EditText) findViewById(R.id.addQuestionEditor);
		correct = (EditText) findViewById(R.id.answer1);
		wrong1 = (EditText) findViewById(R.id.answer2);
		wrong2 = (EditText) findViewById(R.id.answer3);
		wrong3 = (EditText) findViewById(R.id.answer4);

		WebViewClient webClient = new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
                final WebView webview = (WebView) view;
                final WebView.HitTestResult result = webview.getHitTestResult();
                Log.e("ONCLICK",result.toString());

                if (result.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
                	Log.e("SRC_ANCHOR_TYPE",result.toString());
                	QuestionFactoryActivity.this.url = url;
                	validUrl=true;
                }
                else{
                	validUrl=false;
                }
				Log.e("URL",url);
//				QuestionFactoryActivity.this.url = url;
			}
		};
		
		wv = (WebView) findViewById(R.id.webBrowser);
		wv.loadUrl("http://www.google.com/images");
		wv.setWebViewClient(webClient);
		wv.getSettings().setLoadWithOverviewMode(true);
	    wv.getSettings().setUseWideViewPort(true);
	    wv.setInitialScale(getScale());
	    wv.getSettings().setSupportZoom(true);
	    wv.getSettings().setBuiltInZoomControls(true);

		Button send = (Button) findViewById(R.id.newQuestionButton);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				double lat, lng;
				boolean wrongInput=false;
				if (currentLocation==null){
					currentLocation=mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}

				lat = currentLocation.getLatitude();
				lng = currentLocation.getLongitude();
				Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
				List<Address> addresses = null;
				try {
					addresses = gcd.getFromLocation(lat, lng, 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String locationCreated=null;
				if (addresses.size() > 0) 
				    locationCreated=addresses.get(0).getLocality();
				
				removeErrorMessages();
				
				
				String url = QuestionFactoryActivity.this.url;
//				String url = QuestionFactoryActivity.this.url.getText().toString();
				String question = QuestionFactoryActivity.this.question
						.getText().toString();
				String correct = QuestionFactoryActivity.this.correct.getText()
						.toString();
				String wrong1 = QuestionFactoryActivity.this.wrong1.getText()
						.toString();
				String wrong2 = QuestionFactoryActivity.this.wrong2.getText()
						.toString();
				String wrong3 = QuestionFactoryActivity.this.wrong3.getText()
						.toString();
				if (question.equals("")){
					QuestionFactoryActivity.this.question.setError("You must enter a question!");
					wrongInput=true;
				}
				if (correct.equals("")){
					QuestionFactoryActivity.this.correct.setError("Please supply a correct answer!");
					wrongInput=true;
				}
				if (wrong1.equals("")){
					QuestionFactoryActivity.this.wrong1.setError("Please supply an incorrect answer.");
					wrongInput=true;
				}
				if (wrong2.equals("")){
					QuestionFactoryActivity.this.wrong2.setError("Please supply an incorrect answer.");
					wrongInput=true;
				}
				if (wrong3.equals("")){
					QuestionFactoryActivity.this.wrong3.setError("Please supply an incorrect answer.");
					wrongInput=true;
				}
				if (!validUrl){
					
					wrongInput=true;
				}
				if (!wrongInput){
					Question q = new Question();
					q.setImgUrl(url);
					q.setCorrectResponse(correct);
					q.setQuestion(question);
					q.setResponse1(wrong1);
					q.setResponse2(wrong2);
					q.setResponse3(wrong3);
					q.setLocationCreated(locationCreated);
					q.setCreatedBy(Session.getDefaultInstance().getLoggedInUser().getUsername());
					
					Statistics stats = Session.getDefaultInstance().getStats();
					stats.setQuestionsCreated(stats.getQuestionsCreated()+1);
					DatabaseHelper.getDefaultInstance().updateStatistics(stats);
					DatabaseHelper.getDefaultInstance().addQuestion(q);
					QuestionFactoryActivity.this.finish();
				}

			}

		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		View view = getCurrentFocus();
		boolean ret = super.dispatchTouchEvent(event);

		if (view instanceof EditText) {
			View w = getCurrentFocus();
			int scrcoords[] = new int[2];
			w.getLocationOnScreen(scrcoords);
			float x = event.getRawX() + w.getLeft() - scrcoords[0];
			float y = event.getRawY() + w.getTop() - scrcoords[1];

			if (event.getAction() == MotionEvent.ACTION_UP
					&& (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
							.getBottom())) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
			}
		}
		return ret;
	}
	private int getScale(){
	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    int screenHeight = dm.heightPixels;

	    Double val = (new Double(screenHeight-40)/(new Double(1024)));    
	    val = val * 100d;
	    return val.intValue();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(event.getAction() == KeyEvent.ACTION_DOWN){
	        switch(keyCode)
	        {
	        case KeyEvent.KEYCODE_BACK:
	            if(wv.canGoBack()){
	                wv.goBack();
	            }else{
	                finish();
	            }
	            return true;
	        }

	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private void removeErrorMessages(){
		QuestionFactoryActivity.this.question.setError(null);
		QuestionFactoryActivity.this.correct.setError(null);
		QuestionFactoryActivity.this.wrong1.setError(null);
		QuestionFactoryActivity.this.wrong2.setError(null);
		QuestionFactoryActivity.this.wrong3.setError(null);
		
		
	}
	

}
