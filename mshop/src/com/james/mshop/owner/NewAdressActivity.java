package com.james.mshop.owner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.owner.MyadressActivity;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.util.Content;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewAdressActivity extends Base3Activity implements OnClickListener{

	private EditText mEt_Name;
	private EditText mEt_Telephone;
	private EditText mEt_Detail;
	private Spinner mSpinner_Province;
	private Spinner mSpinner_City;
	private Spinner mSpinner_Zone;
	
	private String mCity_ID;
	
	private ArrayList<String> mCityIdList=new ArrayList<String>();
	private ArrayList<String> mCityNameList=new ArrayList<String>();
	
	private ArrayList<String> mAreaIdList=new ArrayList<String>();
	private ArrayList<String> mAreaNameList=new ArrayList<String>();
	private String name;
	private String detail;
	protected String mCurrent_areaId;
	private String mEmber_ID;
	public NewAdressActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_adress);
		//獲得member_id
		UILApplication application = (UILApplication) getApplication();
		       mEmber_ID = application.getmMember_Id();
		       Log.e("ModifyAdressActivity", "mEmber_ID"+mEmber_ID);
		//找控件
		findId();
		//下載城市地點信息
		downLoadCity();
		//下載地區地點信息
	    selectItem();
	}
	private void selectItem() {
		
		mSpinner_Province.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			mCity_ID = mCityIdList.get(position);
			Log.e("mSpinner_Province", ""+mCity_ID);
			mAreaIdList.clear();
			mAreaNameList.clear();
			downLoadArea(mCity_ID);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		mSpinner_City.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (mAreaIdList.size()>0) {
					   mCurrent_areaId = mAreaIdList.get(position);
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
	private void downLoadCity() {
                new 		CityInfoAsyTask().execute(Content.CITY_LIST);
	}
	private void downLoadArea(String string_CityId) {
          new 		AreaInfoAsyTask().execute("http://josie.i3.com.hk/mshop/json/m_arealist.php?cityid="+string_CityId);
}
	class CityInfoAsyTask extends AsyncTask<String, Void, String>{  
		 
		private String string_CityId;
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
				string_CityId = jsonObject2.getString("cityid");
				String	 string_CityName = jsonObject2.getString("cityname");
				mCityIdList.add(string_CityId);
				mCityNameList.add(string_CityName);
				}
				//初始化spinner
					initSpinner();
					//下載地區信息
					//downLoadArea(mCityIdList.get(0));
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
	class AreaInfoAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
				String	 string_AreaId= jsonObject2.getString("areaid");
				String	 string_AreaName = jsonObject2.getString("areaname");
				mAreaIdList.add(string_AreaId);
				mAreaNameList.add(string_AreaName);
				Log.e("AreaInfoAsyTask", ""+string_AreaName);
				}
				//初始化spinner
					initAreaSpinner();	
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
	class SummitAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String code = jsonObject.getString("code");
				Log.e("SummitAsyTask", code);
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
	private void initSpinner() {
		ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(this, R.layout.item_spinner, mCityNameList);
		mSpinner_Province.setAdapter(arrayAdapter1);
		
	}
	private void initAreaSpinner() {
		ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, R.layout.item_spinner, mAreaNameList);
		mSpinner_City.setAdapter(arrayAdapter);
	}
	private void findId() {
      mEt_Name = (EditText) findViewById(R.id.et_adda_name);
      mEt_Telephone = (EditText) findViewById(R.id.et_adda_telephone);	
      mEt_Detail = (EditText) findViewById(R.id.et_adda_detail);	
      findViewById(R.id.tv_newadress_confirm).setOnClickListener(this);
      
      mSpinner_Province = (Spinner) findViewById(R.id.spinner_province);
      mSpinner_City = (Spinner) findViewById(R.id.spinner_city);
	}

	public  void btn_confirm() {
		String string_Name = mEt_Name.getText().toString();
		String string_TelePhone= mEt_Telephone.getText().toString();
		String string_Detail = mEt_Detail.getText().toString();
		try {
			name = URLEncoder.encode(new String(string_Name), "UTF-8");
			detail = URLEncoder.encode(new String(string_Detail), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ("".equals(string_Name)) {
			showDialog(2);
		}
		else {
			if ("".equals(string_TelePhone)) {
				showDialog(1);
			}
			else {
				if ("".equals(string_Detail)) {
					showDialog(3);
				}
				else {
					//提交信息
					new SummitAsyTask().execute(Content.ADD_ADRESS+"mid="+mEmber_ID+
				"&name=" +name+
				"&tel="+string_TelePhone+
				"&cityid="+mCity_ID+
				"&areaid="+mCurrent_areaId+
				"&address="+detail);
					Toast.makeText(NewAdressActivity.this, "新地址添加成功", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(NewAdressActivity.this,MyadressActivity.class));
				}
			}
		}
	}
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		if (id==1) {
			
			return new AlertDialog.Builder(this).setTitle("提示").setMessage("請輸入聯繫電話").setPositiveButton("確定", null).create();
		}
        if (id==2) {
			
			return new AlertDialog.Builder(this).setTitle("提示").setMessage("請輸入收貨人").setPositiveButton("確定", null).create();
		}
        if (id==3) {

            return new AlertDialog.Builder(this).setTitle("提示").setMessage("請輸入詳細地址").setPositiveButton("確定", null).create();
        }
       if (id==4) {

            return new AlertDialog.Builder(this).setTitle("提示").setMessage("請再次輸入密碼").setPositiveButton("確定", null).create();
      }
       else {
             return new AlertDialog.Builder(this).setTitle("提示").setMessage("兩次密碼不一致，請重新輸入").setPositiveButton("確定", null).create();
         }
	     }
	@Override
	public void onClick(View v) {
 switch (v.getId()) {
case R.id.tv_newadress_confirm:
	btn_confirm();
	break;

default:
	break;
}		
	}

}
