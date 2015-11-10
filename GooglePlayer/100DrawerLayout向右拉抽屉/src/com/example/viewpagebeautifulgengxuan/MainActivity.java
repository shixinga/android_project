package com.example.viewpagebeautifulgengxuan;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * 为了兼容低版本（低于4.0），需要extends ActionBarActivity，因为ActionBarActivity里面有getSupportActionBar()
 * @author Administrator
 *
 */
public class MainActivity extends ActionBarActivity {

	private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获取在values/arrays.xml里面的array
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item,R.id.tv_name, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
     // Show the Up button in the action bar.

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
               R.drawable.ic_drawer_am,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(R.string.title_activity_add);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle(R.string.drawer_title);
            }
        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Pressing the app icon in the action bar will navigate to the parent activity.
        getSupportActionBar().setHomeButtonEnabled(true);
    //  让开关和actionbar建立关系 
        mDrawerToggle.syncState();
	}

	/**
	 * 下面这个override是用于logo点击后拉出抽屉用的
	 */
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	     // Pass the event to ActionBarDrawerToggle, if it returns
	     // true, then it has handled the app icon touch event
	     if (mDrawerToggle.onOptionsItemSelected(item)) {
	         return true;
	     }
	     // Handle your other action bar items...

	     return super.onOptionsItemSelected(item);
	}

	
	
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
	    // Create a new fragment and specify the planet to show based on position
		PlanetFragment fragment = new PlanetFragment();
	    // Insert the fragment by replacing any existing fragment
	    FragmentManager fragmentManager = getSupportFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, fragment)
	                   .commit();

	    // Highlight the selected item, update the title, and close the drawer
	    mDrawerList.setItemChecked(position, true);
	    setTitle(mPlanetTitles[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
//	    mTitle = title;
//	    getSupportActionBar().setTitle(mTitle);
	    getSupportActionBar().setTitle(title);
	}
}
