package com.james.mshop;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;

import com.james.mshop.IndexActivity.CarNumAscTask;
import com.james.mshop.owner.OwnerActivity;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.webdesign688.mshop.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Base3Activity implements OnClickListener ,PlatformActionListener,Callback{

	private EditText mEt_Account;
	private EditText mEt_Password;
	private UILApplication application;
	private String name;
	private String mMember_id;
	private static final int MSG_TOAST = 1;
	private static final int MSG_ACTION_CCALLBACK = 2;
	private static final int MSG_CANCEL_NOTIFY = 3;
	public LoginActivity() {
		super(R.string.hello_world);
	}
	public ArrayList<String> mQty=new ArrayList<String>();
	private int plat;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		application = (UILApplication) getApplication();
		findId();
		//初始化Sdk
		ShareSDK.initSDK(this);	
	}
	@Override
	protected void onDestroy() {
		ShareSDK.stopSDK(this);	
		super.onDestroy();
	}
	private void findId() {
		mEt_Account = (EditText) findViewById(R.id.et_account);
		mEt_Password = (EditText) findViewById(R.id.et_password);
		mEt_Account.setText("d");
		mEt_Password.setText("d");

		findViewById(R.id.tv_login_weibo).setOnClickListener(this);
		findViewById(R.id.tv_login_register).setOnClickListener(this);
		findViewById(R.id.tv_login_login).setOnClickListener(this);
		findViewById(R.id.tv_login_forget).setOnClickListener(this);
		findViewById(R.id.tv_login_qq).setOnClickListener(this);
		findViewById(R.id.tv_login_facebook).setOnClickListener(this);
	}
	//回到主菜單
            public void btn_index(View v) {
	              startActivity(new Intent(this,IndexActivity.class));
	              finish();
			}
			//購物車
			public void btn_shoppingcar(View v) {
				startActivity(new Intent(this,ShopCarActivity.class));
				finish();
			}
	//登入
	public void btn_login() {
		String account = mEt_Account.getText().toString();
		String password = mEt_Password.getText().toString();
		if ("".equals(account)) {
			showDialog(3);
		}
		else {
			if ("".equals(password)) {
				showDialog(4);
			}
			else {
			//	new LoginAsyTask().execute(Content.URL_LOGIN+"email="+account+"&pwd="+password);
			//	new LoginAsyTask().execute(Content.URL_LOGIN+"email="+"ljppff@163.com"+"&pwd="+"aaaaaa");
				new LoginAsyTask().execute(Content.URL_LOGIN+"email=test@test.com&pwd=123456");
			}
		}
		
		
		
		/*if ("admin".equals(account)&&"admin".equals(password)) {
			Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
			//設置登錄狀態
			boolean isLogined=true;
			application.setLoginTag(isLogined);
		}
		else {
			showDialog(1);
		}*/
	}
	//註冊
	public void btn_register() {
		startActivity(new Intent(this,RegisterActivity.class));
		finish();
	}
	class LoginAsyTask extends AsyncTask<String, Void, String>{
        private JSONObject jsonObject;
		
		@Override
       protected void onPostExecute(String result) {
       	super.onPostExecute(result);
       	try {
			jsonObject = new JSONObject(result);
			String code = jsonObject.getString("code");
			if ("1".equals(code)) {
				
				JSONObject jsonObject2 = jsonObject.getJSONObject("data");
				mMember_id = jsonObject2.getString("id");
				name = jsonObject2.getString("name");
				String   level = jsonObject2.getString("level");
				//登陸成功
				Log.e("Login_name", name);
				Log.e("Login_level", level);
				Log.e("LoginActivity", "mMember_id"+mMember_id);
				boolean isLogined=true;
				application.setLoginTag(isLogined);
				application.setmMember_Id(mMember_id);
				application.setmString_actionbar3("個人中心");
				//獲得購物車商品數量
				getCarNum(mMember_id);
				Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this,OwnerActivity.class);
				intent.putExtra("name", name);
				startActivity(intent);
				finish();
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
			String str = params[0];
			return getJson.getData(str);
		}
		}
	private void getCarNum(String mMember_id2) {
		 new CarNumAscTask().execute(Content.SHOP_LIST+mMember_id2);    			
	}
	class CarNumAscTask  extends   AsyncTask<String, Void, String>{
		@Override
		protected void onPostExecute(String result) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
		                String     string_Qty = jsonObject2.getString("qty"); 
		                mQty.add(string_Qty);
				  }
				  //計算總商品件數
				  int  mCarNum=0;
				  for (int i = 0; i < mQty.size(); i++) {
					              int num = Integer.valueOf(mQty.get(i)) ;
					              mCarNum+=num;
				}
				  application.setNum_Shopcar(mCarNum);			
				  Log.e("LoginApplication", ""+mCarNum);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onPostExecute(result);
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
			return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("賬號或密碼不正確，請重新輸入").setPositiveButton(R.string.confirm, null).create();
		}
		else if(id==2) {
			
			return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
		}
         else if(id==3) {
			
			return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("請輸入電郵地址").setPositiveButton(R.string.confirm, null).create();
		}
else {
	return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("請輸入密碼").setPositiveButton(R.string.confirm, null).create();
}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login_login:
		  	    btn_login();
			    break;
			case R.id.tv_login_register:
				btn_register();
				break;
			case R.id.tv_login_weibo:
				login_Weibo();
				break;
			case R.id.tv_login_qq:
				login_qq();
				break;
			case R.id.tv_login_facebook:
				login_Facebook();
				break;
			case R.id.tv_login_forget:
			Intent intent = new Intent(LoginActivity.this,ForgetPasswordAcitivity.class);
			startActivity(intent);
				break;

		default:
			break;
		}

	}
	private void login_Facebook() {
		plat=3;
		Facebook pf = new Facebook(LoginActivity.this);
		pf.removeAccount();
		pf.SSOSetting(true);
		//设置监听
		//Setting authorization listener
		pf.setPlatformActionListener(LoginActivity.this);
		//执行授权
		//Perform authorization
		pf.showUser(null);		
	}
	private void login_qq() {
		plat=2;
		QZone pf = new QZone(LoginActivity.this);
		pf.removeAccount();
		pf.SSOSetting(false);
		//设置监听
		//Setting authorization listener
		pf.setPlatformActionListener(LoginActivity.this);
		//执行授权
		//Perform authorization
		pf.showUser(null);			
	}
	private void login_Weibo() {
		plat=1;
		SinaWeibo pf = new SinaWeibo(LoginActivity.this);
		pf.removeAccount();
		pf.SSOSetting(true);
		//设置监听
		//Setting authorization listener
		pf.setPlatformActionListener(LoginActivity.this);
		//执行授权
		//Perform authorization
		
		pf.showUser(null);		
		
	/*	Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
		sina.setPlatformActionListener(this);
		//关闭SSO授权
		sina.SSOSetting(true);
		sina.showUser(null);*/

	}
	@Override
	public boolean handleMessage(Message msg) {
		Platform  pf = null;
			switch (msg.arg1) {
				case 1: { // 成功, successful notification
          switch (plat) {
		case 1:
		pf = ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
			break;
        case 2:
         pf = ShareSDK.getPlatform(LoginActivity.this, QZone.NAME);
			break;
         case 3:
         pf = ShareSDK.getPlatform(LoginActivity.this, Facebook.NAME);
	     break;

		default:
			break;
		}
					//授权成功后,获取用户信息，要自己解析，看看oncomplete里面的注释
					//ShareSDK只保存以下这几个通用值
					Toast.makeText(this, "成功授權", Toast.LENGTH_SHORT).show();
					
					Log.e("sharesdk use_id", pf.getDb().getUserId()); //获取用户id
					Log.e("sharesdk use_name", pf.getDb().getUserName());//获取用户名称
					Log.e("sharesdk use_icon", pf.getDb().getUserIcon());//获取用户头像
					//pf.author()这个方法每一次都会调用授权，出现授权界面
					//如果要删除授权信息，重新授权
					//pf.getDb().removeAccount();
					//调用后，用户就得重新授权，否则下一次就不用授权
					
				}
				break;
				case 2: { // 失败, fail notification
					Toast.makeText(this, "授權失敗", Toast.LENGTH_SHORT).show();
				}
				break;
				case 3: { // 取消, cancel notification
					Toast.makeText(this, "取消授權", Toast.LENGTH_SHORT).show();
				}
			}
	return false;
	}
	@Override
	public void onCancel(Platform platform, int action) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
		
	}
	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> arg2) {
		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = platform;
		UIHandler.sendMessage(msg, this);
	}
	@Override
	public void onError(Platform arg0, int action, Throwable t) {
		t.printStackTrace();
		t.getMessage();

		Message msg = new Message();
		msg.what = MSG_ACTION_CCALLBACK;
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = t;
		UIHandler.sendMessage(msg, this);		
		
	}
}
