package com.james.mshop;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.webdesign688.mshop.R;

public class ForgetPasswordAcitivity extends    Activity implements OnClickListener{
	private EditText mEditText_name;
	private EditText mEt_Code;
	private EditText mEt_Pw;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		findViewById(R.id.tv_secury_obtain).setOnClickListener(this);
		findViewById(R.id.tv_forget_summit).setOnClickListener(this);
		mEditText_name = (EditText) findViewById(R.id.et_forget_name);
		mEt_Pw = (EditText) findViewById(R.id.et_secury_newpw);
		mEt_Code = (EditText) findViewById(R.id.et_secury_code);
	}
	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.tv_forget_summit:
			//提交
			summit();
            break;
		case R.id.tv_secury_obtain:
			//提交獲得驗證碼
			obtainCode();
            break;

		default:
			break;
		}
	}
	private void obtainCode() {

	      String string_Name = mEditText_name.getText().toString();
	      if ("".equals(string_Name)) {
			showDialog(1);
		}
	      else {
	      	  RequestParams params = new RequestParams();
	            List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(4);
	            nameValuePairs.add(new BasicNameValuePair("useremail", string_Name));
	            params.addBodyParameter(nameValuePairs);
	            HttpUtils http = new HttpUtils();
	            http.send(HttpRequest.HttpMethod.POST,
	                    "http://josie.i3.com.hk/mshop/json/forgetpwd.php",
	                    params,
	                    new RequestCallBack<String>() {   

	                        @Override
	                        public void onStart() {
	                        }
	                        @Override
	                        public void onLoading(long total, long current, boolean isUploading) {
	                        }
	                        @Override
	                        public void onSuccess(ResponseInfo<String> responseInfo) {
	                        	Log.e("onSuccess", "upload response:" + responseInfo.result);
	                        	try {
									JSONObject jobject =new JSONObject(responseInfo.result);
									String code =jobject.getString("code");
									if(!TextUtils.isEmpty(code)&&code.equals("1")){
			                        	findViewById(R.id.tv_secury_success).setVisibility(View.VISIBLE);
			                        	Toast.makeText(ForgetPasswordAcitivity.this, "已將驗證碼發送您郵箱，請查收", Toast.LENGTH_SHORT).show();   

									}else if(!TextUtils.isEmpty(code)&&code.equals("0")){
										String msg =jobject.getString("msg");
				                      	Toast.makeText(ForgetPasswordAcitivity.this, msg, Toast.LENGTH_SHORT).show();  
									}
									
								} catch (JSONException e) {
			                      	Toast.makeText(ForgetPasswordAcitivity.this, "未發送成功，", Toast.LENGTH_SHORT).show();  
								}

	                        }
	                        @Override
	                        public void onFailure(HttpException error, String msg) {
	                      	Toast.makeText(ForgetPasswordAcitivity.this, "未發送成功，", Toast.LENGTH_SHORT).show();  
	                        }
	                    });
		}

	}

	private void summit() {
	      String string_Name = mEditText_name.getText().toString();
	      String string_Code= mEt_Code.getText().toString();
	      String string_Pw = mEt_Pw.getText().toString();
	      
	      if ("".equals(string_Name)) {
			showDialog(1);
		}
	      else {
	             if ("".equals(string_Code)) {
					showDialog(2);
				}
	             else {
					if ("".equals(string_Pw)) {
						showDialog(3);
					}
					else {
						 RequestParams params = new RequestParams();
				            List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>(4);
				            nameValuePairs.add(new BasicNameValuePair("username", "test1admin1"));
				            nameValuePairs.add(new BasicNameValuePair("code",string_Code ));
				            nameValuePairs.add(new BasicNameValuePair("newpwd", string_Pw));
				            params.addBodyParameter(nameValuePairs);
				            HttpUtils http = new HttpUtils();
				            http.send(HttpRequest.HttpMethod.POST,
				                    "http://josie.i3.com.hk/mshop/json/forgetpwdcheck.php",
				                    params,
				                    new RequestCallBack<String>() {   

				                        @Override
				                        public void onStart() {
				                        }
				                        @Override
				                        public void onLoading(long total, long current, boolean isUploading) {
				                        }
				                        @Override
				                        public void onSuccess(ResponseInfo<String> responseInfo) {
				                        	Log.e("onSuccess", "upload response:" + responseInfo.result);
				                        	try {
												JSONObject jobject =new JSONObject(responseInfo.result);
												String code =jobject.getString("code");
												if(!TextUtils.isEmpty(code)&&code.equals("1")){
						                        	Toast.makeText(ForgetPasswordAcitivity.this, "密碼已修改，請重新登入", Toast.LENGTH_SHORT).show();   

												}else if(!TextUtils.isEmpty(code)&&code.equals("0")){
													String msg =jobject.getString("msg");
						                        	Toast.makeText(ForgetPasswordAcitivity.this, msg, Toast.LENGTH_SHORT).show();   
												}
												
											} catch (JSONException e) {
					                        	Toast.makeText(ForgetPasswordAcitivity.this, "未修改成功，請重新設置", Toast.LENGTH_SHORT).show();  
											}
				                        	
				                        	
				                        }
				                        @Override
				                        public void onFailure(HttpException error, String msg) {
				                        	Toast.makeText(ForgetPasswordAcitivity.this, "未修改成功，請重新設置", Toast.LENGTH_SHORT).show();  
				                        }
				                    });
					}
				}
	      	}
	}
            @Override
            @Deprecated
            protected Dialog onCreateDialog(int id) {
            	if (id==1) {
					
            		return new AlertDialog.Builder(this).
            				setTitle(R.string.tip).setMessage("請輸入用戶名").setPositiveButton(R.string.confirm, null).create();
				}
                  if (id==2) {
					
            		return new AlertDialog.Builder(this).
            				setTitle(R.string.tip).setMessage("請輸入驗證碼").setPositiveButton(R.string.confirm, null).create();
				}
                  else {
  					
              		return new AlertDialog.Builder(this).
              				setTitle(R.string.tip).setMessage("請輸入新密碼").setPositiveButton(R.string.confirm, null).create();
  				}
            }
}
