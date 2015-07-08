package com.james.mshop;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import com.webdesign688.mshop.R;

@SuppressLint("Instantiatable") public class SecuCodeActivity extends Base3Activity {

	public SecuCodeActivity(int titleRes) {
		super(R.string.hello_world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
	}


}
