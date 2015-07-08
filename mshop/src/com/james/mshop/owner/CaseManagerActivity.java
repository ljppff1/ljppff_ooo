package com.james.mshop.owner;

import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.cases.Case1Activity;
import com.james.mshop.cases.Case2Activity;
import com.james.mshop.cases.Case3Activity;
import com.james.mshop.cases.Case4Activity;
import com.james.mshop.util.AppManager;
import com.james.mshop.util.UILApplication;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class CaseManagerActivity extends TabActivity {
	private  TabHost mTabHost;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case_manager);
		//³õÊ¼»¯TabHost
		AppManager.getAppManager().addActivity(CaseManagerActivity.this);
		initTabHost();
	UILApplication application = (UILApplication) getApplication();
	application.setmString_actionbar3("Ó††ÎÔ”Çé");

	}
	private void initTabHost() {
		mTabHost = getTabHost();
		TabSpec tabSpec1=mTabHost.newTabSpec("1");
		View  view1=getLayoutInflater().inflate(R.layout.item_shopcar_tab1, null);
		tabSpec1.setIndicator(view1);
		Intent intent1 = new Intent(this,Case1Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		tabSpec1.setContent(intent1);
		mTabHost.addTab(tabSpec1);
		
		TabSpec tabSpec2=mTabHost.newTabSpec("2");
		 View  view2=getLayoutInflater().inflate(R.layout.item_shopcar_tab2, null);
		tabSpec2.setIndicator(view2);
		Intent intent2=new Intent(this,Case2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		tabSpec2.setContent(intent2);
		mTabHost.addTab(tabSpec2);
		
		TabSpec tabSpec3=mTabHost.newTabSpec("3");
		 View  view3=getLayoutInflater().inflate(R.layout.item_shopcar_tab3, null);
		tabSpec3.setIndicator(view3);
		Intent intent3 = new Intent(this,Case3Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		tabSpec3.setContent(intent3);
		mTabHost.addTab(tabSpec3);
		TabSpec tabSpec4=mTabHost.newTabSpec("4");
		 View  view4=getLayoutInflater().inflate(R.layout.item_shopcar_tab4, null);
		tabSpec4.setIndicator(view4);
		Intent intent4 = new Intent(this,Case4Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		tabSpec4.setContent(intent4);
		mTabHost.addTab(tabSpec4);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_detail, menu);
		return true;
	}

}
