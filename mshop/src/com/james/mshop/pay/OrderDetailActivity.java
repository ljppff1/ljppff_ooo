package com.james.mshop.pay;

import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.util.Bean;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class OrderDetailActivity extends Base3Activity {

	private TextView mTv_Summit;
	private String mEmber_ID;
	private UILApplication application;
	private String string_PayStyle;
	private String string_PayName;
     public OrderDetailActivity(){
    	 super(R.string.hello_world);
     }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		
		TextView textView_Account1= (TextView) findViewById(R.id.tv_order_detail_account1);
		TextView textView_Account2= (TextView) findViewById(R.id.tv_order_detail_account2);
		TextView textView_Receive= (TextView) findViewById(R.id.tv_order_detail_receiver);
		TextView textView_Tel= (TextView) findViewById(R.id.tv_order_detail_tele);
		TextView textView_AdressName= (TextView) findViewById(R.id.tv_order_detail_adressname);
		TextView textView_Paystyle= (TextView) findViewById(R.id.tv_order_detail_paystyle);
		mTv_Summit = (TextView) findViewById(R.id.tv_order_detail_summit);
		application = (UILApplication) getApplication();
				       mEmber_ID = application.getmMember_Id();
				  	 Log.e("OrderDetailActivity", "mEmber_ID"+mEmber_ID);
		//得到信息
		Intent intent = getIntent();
		string_PayStyle = intent.getStringExtra("pay_style");
		string_PayName = intent.getStringExtra("pay_name");
		
		final String m_Aid = Bean.getAdressId();
		String mString_adressName = Bean.getAdressName();
		final String mString_account = Bean.getAccount();
		String mString_receiever = Bean.getReceiver();
		String mString_telephone= Bean.getTelephone();
		//点击事件
		mTv_Summit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			new SummitAsyTask().execute("http://josie.i3.com.hk/mshop/json/order_submit.php?mid=" +mEmber_ID+
					"&aid=" +m_Aid+
					"&cid=" +
					"&pid="+string_PayStyle);
			}
		});
		
		Log.e("OrderDetailActivity", m_Aid);
		Log.e("OrderDetailActivity", mString_adressName);
		Log.e("OrderDetailActivity", mString_account);
		//设置信息
		textView_Account1.setText(mString_account);
		textView_Account2.setText(mString_account);
		textView_AdressName.setText(mString_adressName);
		textView_Paystyle.setText(string_PayName);
		textView_Tel.setText(mString_telephone);
		textView_Receive.setText(mString_receiever);
		
		
	}
	 class SummitAsyTask extends AsyncTask<String, Void, String>{  
		 
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					String string = jsonObject.getString("code");
					if ("1".equals(string)) {
						Intent intent = new Intent(OrderDetailActivity.this,OrderConfirmActivity.class);
						//設置
						application.setNum_Shopcar(0);
						application.setmString_actionbar3("提交訂單");
						intent.putExtra("pay_style", string_PayStyle);
						intent.putExtra("pay_name", string_PayName);
						startActivity(intent);
					}
					else {
						showDialog(1);
					}
				} catch (JSONException e) {
					showDialog(2);
					e.printStackTrace();
				}
			}
				@Override
				protected String doInBackground(String... params) {
					String str=params[0];
					return getJson.getData(str);
				}
				
				}
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		if (id==1) {
			return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("未成功提交訂單").setPositiveButton(R.string.confirm, null).create();
		}
		else {
			return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
		}
	}

}
