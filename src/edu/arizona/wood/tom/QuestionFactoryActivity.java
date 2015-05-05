package edu.arizona.wood.tom;

import android.app.Activity;
import android.content.Context;
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

public class QuestionFactoryActivity extends Activity {

	EditText question, correct, wrong1, wrong2, wrong3;
	WebView wv;
	String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionfactory);
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
                }

                if (result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                	Log.e("SRC_IMAGE_ANCHOR_TYPE",result.toString());
                }

                if (result.getType() == HitTestResult.IMAGE_TYPE) {
                	Log.e("IMAGE_TYPE",result.toString());
                }
				Log.e("URL",url);
				QuestionFactoryActivity.this.url = url;
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
	    wv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                final WebView webview = (WebView) v;
                final WebView.HitTestResult result = webview.getHitTestResult();
                Log.e("ONCLICK",result.toString());

                if (result.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
                	Log.e("SRC_ANCHOR_TYPE",result.toString());
                }

                if (result.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                	Log.e("SRC_IMAGE_ANCHOR_TYPE",result.toString());
                }

                if (result.getType() == HitTestResult.IMAGE_TYPE) {
                	Log.e("IMAGE_TYPE",result.toString());
                }

			}
        });

		Button send = (Button) findViewById(R.id.newQuestionButton);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
				Question q = new Question();
				q.setImgUrl(url);
				q.setCorrectResponse(correct);
				q.setQuestion(question);
				q.setResponse1(wrong1);
				q.setResponse2(wrong2);
				q.setResponse3(wrong3);
				DatabaseHelper.getDefaultInstance().addQuestion(q);
				QuestionFactoryActivity.this.finish();
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
}
