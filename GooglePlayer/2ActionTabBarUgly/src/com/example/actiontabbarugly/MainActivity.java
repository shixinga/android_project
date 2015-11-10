package com.example.actiontabbarugly;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Notice that setContentView() is not used, because we use the root
	    // android.R.id.content as the container for each fragment

	    // setup action bar for tabs
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.setDisplayShowTitleEnabled(false);

	    Tab tab = actionBar.newTab()
	                       .setText("aaaaa")
	                       .setTabListener(new TabListener<AFragment>(
	                               this, "artist", AFragment.class));
	    actionBar.addTab(tab);

	    tab = actionBar.newTab()
	                   .setText("bb")
	                   .setTabListener(new TabListener<BFragment>(
	                           this, "album", BFragment.class));
	    actionBar.addTab(tab);
	}

}
