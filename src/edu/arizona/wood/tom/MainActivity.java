package edu.arizona.wood.tom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import edu.arizona.foodcrack.R;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


//        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Simple"),
//                FragmentStackSupport.CountingFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Contacts"),
//                LoaderCursorSupport.CursorLoaderListFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("custom").setIndicator("Custom"),
//                LoaderCustomSupport.AppListFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("throttle").setIndicator("Throttle"),
//                LoaderThrottleSupport.ThrottledLoaderListFragment.class, null);
    }
}
