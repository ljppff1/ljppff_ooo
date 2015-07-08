package com.james.mshop.pay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.pay.OrderDetailActivity.SummitAsyTask;
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
import android.widget.Toast;

public class OrderConfirmActivity extends Base3Activity {

	private TextView mTv_Summit;
	private TextView textView_Account2;
	private   String mTotalhk;
	private String mEmber_ID;
	private UILApplication application;
	private String string_PayName;
	private String no;
	private String shiphk;
	private String name;
	public OrderConfirmActivity(){
		super(R.string.hello_world);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_confirm);
		TextView textView_Account1= (TextView) findViewById(R.id.tv_order_detail_account1);
		textView_Account2 = (TextView) findViewById(R.id.tv_order_detail_account2);
		TextView textView_Receive= (TextView) findViewById(R.id.tv_order_detail_receiver);
		TextView textView_Tel= (TextView) findViewById(R.id.tv_order_detail_tele);
		TextView textView_AdressName= (TextView) findViewById(R.id.tv_order_detail_adressname);
		TextView textView_Paystyle= (TextView) findViewById(R.id.tv_order_detail_paystyle);
		mTv_Summit = (TextView) findViewById(R.id.tv_order_detail_summit);
		application = (UILApplication) getApplication();
		       mEmber_ID = application.getmMember_Id();
		  	 Log.e("OrderConfirmActivity", "mEmber_ID"+mEmber_ID);
		
		//得到信息
		Intent intent = getIntent();
		final String string_PayStyle = intent.getStringExtra("pay_style");
		 string_PayName= intent.getStringExtra("pay_name");
		//獲得訂單信息
		downLoad();
		final String m_Aid = Bean.getAdressId();
		String mString_adressName = Bean.getAdressName();
		final String mString_account = Bean.getAccount();
		String mString_receiever = Bean.getReceiver();
		String mString_telephone= Bean.getTelephone();
		//点击事件
		mTv_Summit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if ("1".equals(string_PayStyle)) {
					Intent intent2 = new Intent(OrderConfirmActivity.this,PayPalActivity.class);
					intent2.putExtra("NAME", name);
					intent2.putExtra("HK",shiphk);
					intent2.putExtra("ID", no);
             

					Toast.makeText(OrderConfirmActivity.this, "正在跳轉。。。", Toast.LENGTH_LONG).show();
					application.setmString_actionbar3("付款");
					//intent2.putExtra("total", mTotalhk);
					startActivity(intent2);
				}
				else if("2".equals(string_PayStyle)) {
					Toast.makeText(OrderConfirmActivity.this, "暫時未接入支付寶付款", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(OrderConfirmActivity.this, "暫時未接入財富通", Toast.LENGTH_SHORT).show();
				}
				}
		});
		Log.e("OrderDetailActivity", m_Aid);
		Log.e("OrderDetailActivity", mString_adressName);
		
		Log.e("OrderDetailActivity", mString_account);
		//设置信息
		textView_Account1.setText(mString_account);
		mTv_Summit.setText(string_PayName);
		textView_AdressName.setText(mString_adressName);
		textView_Paystyle.setText(string_PayName);
		textView_Tel.setText(mString_telephone);
		textView_Receive.setText(mString_receiever);
	}
	private void downLoad() {
		new OrderAsyTask().execute("http://josie.i3.com.hk/mshop/json/m_orderlist.php?mid="+mEmber_ID+"&st=0");
	}
	
	 class SummitAsyTask extends AsyncTask<String, Void, String>{  
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					String string = jsonObject.getString("code");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
				@Override
				protected String doInBackground(String... params) {
					String str=params[0];
					return getJson.getData(str);
				}
				}
	 class OrderAsyTask extends AsyncTask<String, Void, String>{  
		 
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONArray array = jsonObject.getJSONArray("data");
						 JSONObject jsonObject2 = array.getJSONObject(0);
					String	string_Id= jsonObject2.getString("id");
					new OrderDetailAsyTask().execute("http://josie.i3.com.hk/mshop/json/m_orderdetail.php?" +
							"mid="+mEmber_ID+
							"&" +
							"id="+string_Id);
					  
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
	 class OrderDetailAsyTask extends AsyncTask<String, Void, String>{  
		 
			
			
			
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					JSONObject data = jsonObject.getJSONObject("data");
					 no = data.getString("no");
					 shiphk = data.getString("shiphk");
					String shiprmb = data.getString("shiprmb");
					 mTotalhk = data.getString("totalhk");
					  JSONArray product = data.getJSONArray("product");
					  name = product.getJSONObject(0).getString("name");
					String totalrmb = data.getString("totalrmb");
					TextView tv_Num= (TextView) findViewById(R.id.tv_order_detail_num);
					TextView tv_Ship= (TextView) findViewById(R.id.tv_order_detail_shipaccount);
					tv_Ship.setText("HK$ "+shiphk+"  "+"￥"+shiprmb);
					Log.e("OrderConfirm", shiphk);
					Log.e("OrderConfirm", shiprmb);
					tv_Num.setText(no);
					textView_Account2.setText("HK$ "+mTotalhk+"  "+"￥"+totalrmb);
					
					  
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
	 @Deprecated
		protected Dialog onCreateDialog(int id) {
			// TODO Auto-generated method stub
			return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
		}


}
