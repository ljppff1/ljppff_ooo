package com.james.mshop.pay;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.util.UILApplication;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ChoicePayActivity extends Base3Activity  {

	private String m_Aid;
	private TextView mTv_Paypal;
	private TextView mTv_Zhifubao;
	private TextView mTv_CaiFutong;
	private UILApplication application;
   public ChoicePayActivity(){
	   super(R.string.hello_world);
   }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice_pay);
		mTv_Paypal = (TextView) findViewById(R.id.tv_paypal);
		mTv_Zhifubao = (TextView) findViewById(R.id.tv_zhifubao);
		mTv_CaiFutong = (TextView) findViewById(R.id.tv_zhuanzhang);
           application= (UILApplication) getApplication();
		//點擊事件
		click();
		//獲得aid
		Intent intent = getIntent();
		m_Aid = intent.getStringExtra("aid");
	}

	private void click() {
		mTv_Paypal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1=new Intent(ChoicePayActivity.this,OrderDetailActivity.class);
				intent1.putExtra("pay_style", "1");
				intent1.putExtra("pay_name", "Paypal付款");
				startActivity(intent1);
				application.setmString_actionbar3("訂單信息");
			}
		});		
		mTv_Zhifubao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent2=new Intent(ChoicePayActivity.this,OrderDetailActivity.class);
				intent2.putExtra("pay_style", "2");
				intent2.putExtra("pay_name", "支付寶付款");
				startActivity(intent2);
				application.setmString_actionbar3("訂單信息");
			}
		});		
		mTv_CaiFutong.setOnClickListener(new OnClickListener() {
	
	       @Override
	     public void onClick(View v) {
		  Intent intent3=new Intent(ChoicePayActivity.this,OrderDetailActivity.class);
		  intent3.putExtra("pay_style", "3");
		  intent3.putExtra("pay_name", "財付通付款");
		  startActivity(intent3);
		  application.setmString_actionbar3("訂單信息");
	}
});		
	}


}
