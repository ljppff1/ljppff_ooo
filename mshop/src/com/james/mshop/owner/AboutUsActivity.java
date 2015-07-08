package com.james.mshop.owner;

import com.james.mshop.Base3Activity;
import com.webdesign688.mshop.R;
import com.james.mshop.util.UILApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class AboutUsActivity extends Base3Activity {

	public AboutUsActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
	}

}
