package com.james.mshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.owner.NewAdressActivity;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import com.webdesign688.mshop.R;

public class RegisterActivity extends Base3Activity  implements  OnClickListener {

	private EditText mEt_mail;
	private EditText mEt_name;
	private EditText mEt_password;
	private UILApplication application;
	public RegisterActivity() {
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		application = (UILApplication) getApplication();
		findId();
	}
	private void findId() {
                      mEt_mail = (EditText) findViewById(R.id.et_register_email);	
                      mEt_name = (EditText) findViewById(R.id.et_register_name);		
                      mEt_password = (EditText) findViewById(R.id.et_register_password);		
                      findViewById(R.id.tv_register_andlogin).setOnClickListener(this);
                      findViewById(R.id.tv_register_longin).setOnClickListener(this);
                      
	}
   //登入
	public  void btn_login() {
              startActivity(new Intent(this,LoginActivity.class));
              finish();
	}
	//註冊并登入
	public void btn_register() {
		   String email = mEt_mail.getText().toString();
		   String name   = mEt_name.getText().toString();
		   String password  = mEt_password.getText().toString();
		   boolean isEmail = isEmail(email);
			
		   if ("".equals(email)) {
			showDialog(1);
		}
		   else {
			   if (isEmail) {
				   if ("".equals(name)) {
					   showDialog(6);
				   }
				   else {
					
					   if ("".equals(password)) {
						   showDialog(3);
					   }
					   else {
						   if (password.length()>5) {
							
							   //註冊成功
							   new RegistAsyTask().execute(Content.URL_REGIT+
									   "email=" +email+
									   "&pwd=" +password+
									   "&name="+name);
						}else {
							showDialog(9);
						}
					}
				}
			}
			   else {
				showDialog(7);
			}
			
		}
		
	}
	 public static boolean isEmail(String email){     
	     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	        Pattern p = Pattern.compile(str);     
	        Matcher m = p.matcher(email);     
	        return m.matches();     
	    } 
	class RegistAsyTask extends AsyncTask<String, Void, String>{
        private JSONObject jsonObject;
		@Override
       protected void onPostExecute(String result) {
       	super.onPostExecute(result);
       	try {
			jsonObject = new JSONObject(result);
			String code = jsonObject.getString("code");
			if ("1".equals(code)) {
				String mData = jsonObject.getString("data");
				Log.e("Login_name", mData);
				Log.e("Login_code", code);
				boolean isLogined=true;
				application.setLoginTag(isLogined);
				application.setmMember_Id(mData);
				startActivity(new Intent(RegisterActivity.this,NewAdressActivity.class));
				finish();
			}
			else {
				showDialog(8);
				Log.e("Login_code", code);
			}
			/*JSONObject jsonObject2 = jsonObject.getJSONObject("data");
			String   name = jsonObject2.getString("name");
			String   level = jsonObject2.getString("level");*/
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
			@Override
			@Deprecated
			protected Dialog onCreateDialog(int id) {
				
				if (id==1) {
					
					return new AlertDialog.Builder(this).setTitle("提示").setMessage("請輸入Email地址").setPositiveButton(R.string.confirm, null).create();
				}
				else if (id==2) {
					
                	return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
				}
				else if (id==3) {
	
	                return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("請輸入密碼").setPositiveButton(R.string.confirm, null).create();
                }
				else  if (id==4) {
	
	                return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("請再次輸入密碼").setPositiveButton(R.string.confirm, null).create();
              }
               else if (id==5) {
	                 return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("兩次密碼不一致，請重新輸入").setPositiveButton(R.string.confirm, null).create();
                 }
               else if (id==6) {
            	   return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("請輸入姓名").setPositiveButton(R.string.confirm, null).create();
			}
               else if(id==7) {
            	   return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("郵箱格式不正確").setPositiveButton(R.string.confirm, null).create();
			}
               else if(id==8) {
            	   return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("註冊有誤，請重新註冊").setPositiveButton(R.string.confirm, null).create();
			}
               else {
            	   return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("密碼不能少於六位").setPositiveButton(R.string.confirm, null).create();
			}
			     }
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_register_longin:
					btn_login();
					break;
                 case R.id.tv_register_andlogin:
                	 btn_register();
					break;

				default:
					break;
				}
			}
}
