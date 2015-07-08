package com.james.mshop;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.util.AppManager;
import com.james.mshop.util.UILApplication;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.webdesign688.mshop.R;

public class Base1Activity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected LeftFrament mFrag;
	private UILApplication       application;
	private EditText mEt_Search;

	public Base1Activity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);
      application = (UILApplication) getApplication();
  	AppManager.getAppManager().addActivity(this);
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new LeftFrament();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (LeftFrament)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
      android.app.ActionBar actionBar =getActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
       actionBar.setDisplayShowCustomEnabled(true);//◊‘∂®¡x     
       actionBar.setLogo(R.drawable.icon_menu);
       actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.touming));
       View view=getLayoutInflater().inflate(R.layout.item_actionbar, null);
       
       mEt_Search = (EditText) view.findViewById(R.id.et_action_search);
      TextView mTv_Num= (TextView) view.findViewById(R.id.tv_actionbar_carnum);
       int num_Shopcar = application.getNum_Shopcar();
       mTv_Num.setText(num_Shopcar+"");
       //´@µ√À—À˜µƒÉ»»›
       obtainContent();
       String string_actionbar = application.getmString_actionbar();
       ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButton_actionbar);
      imageButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			UILApplication application = (UILApplication) getApplication();
			boolean loginTag = application.getLoginTag();
			if (loginTag) {
				application.setmString_actionbar3("ŸèŒÔ‹á");
				Intent intent = new Intent(Base1Activity.this,ShopCarActivity.class);
				startActivity(intent);
			}
			else {
				application.setmString_actionbar3("µ«»Î");
				Intent intent = new Intent(Base1Activity.this,LoginActivity.class);
				startActivity(intent);
			}
			
			
		}
	});
       
	   actionBar.setCustomView(view);
		/*actionBar.setTitle("‰∏?îü‰ΩïÊ±Ç");//Ê∑ªÂä†Ê†áÈ¢ò
		actionBar.setSubtitle("ÂàòÂæ∑Âç?);//Ê∑ªÂä†Â∞èÊ†áÈ¢?		actionBar.setDisplayHomeAsUpEnabled(true);//‰ΩøÊ†áÈ¢òÂ¢ûÂä†‰∏Ä‰∏™Â∞è<Ôºå‰ΩøÂõæÊ†áÊåâÈíÆÂèØÁÇπÂá?		actionBar.setLogo(R.drawable.ic_launcher);//ËÆæÁΩÆÂõæÊ†á,‰∏çËÆæÁΩÆÂ∞±ÊòØÊú∫Âô®‰∫∫
		actionBar.setDisplayShowCustomEnabled(true);//ÊîØÊåÅËá™ÂÆö‰π?		actionBar.setCustomView(R.layout.rela_actionbar);
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.touming));*/
	}
	protected String obtainContent() {

		String string = mEt_Search.getText().toString();
		return   string;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(AppManager.getAppManager().isMainOrWel()){
			toggle();
			}else{
				AppManager.getAppManager().finishActivity();
			}
			return true;
/*		case R.id.github:
			Util.goToGitHub(this);
*/			
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, (android.view.Menu) menu);
		return true;
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(!AppManager.getAppManager().isMainOrWel()){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			AppManager.getAppManager().finishActivity();
			 return true;
		}
		}
		return super.onKeyDown(keyCode, event);
	}

	
}
