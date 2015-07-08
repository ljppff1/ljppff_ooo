package com.james.mshop.owner;

import com.james.mshop.Base3Activity;
import com.james.mshop.MainFragment;
import com.webdesign688.mshop.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Menu;

public class Tab2Activity extends Base3Activity {
    public Tab2Activity(){
    	super(R.string.hello_world);
    }
    private Fragment mContent;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		mContent = new MainFragment_Case();
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new MainFragment_Case();

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		/*// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		mMenuFragment = new SampleListFragment();
				.replace(R.id.menu_frame,mMenuFragment ).commit();*/

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
}
