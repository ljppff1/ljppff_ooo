package com.james.mshop.owner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyadressActivity extends Base3Activity {

	private ListView mListView;
	private ArrayList<String> mOderIdList=new ArrayList<String>();
	private ArrayList<String> mCityIdList=new ArrayList<String>();
	private ArrayList<String> mAreaIdList=new ArrayList<String>();
	private ArrayList<String> mNameList=new ArrayList<String>();
	
	private ArrayList<String> mTelephoneList=new ArrayList<String>();
	private ArrayList<String> mCityNameList=new ArrayList<String>();
	private ArrayList<String> mAreaNameList=new ArrayList<String>();
	
	private ArrayList<String> mAdressList=new ArrayList<String>();
	private MyAdapter adapter;
	private UILApplication mApplication;
	private String mEmber_ID;
	public MyadressActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myadress);
		mListView = (ListView) findViewById(R.id.listView_owner_myadress);
		//獲得member_id
		mApplication = (UILApplication) getApplication();
	 mEmber_ID = mApplication.getmMember_Id();
	 Log.e("ModifyAdressActivity", "mEmber_ID"+mEmber_ID);
		//下載信息
		downLoad();
		//點擊Item
		selectItem();
		
	}
	private void selectItem() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}
	private void downLoad() {
 		new DownLoadAsyTask().execute(Content.ADRESS_LIST+mEmber_ID);
}
	class DownLoadAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
				String	string_AdressId= jsonObject2.getString("aid");
				String	string_CityId= jsonObject2.getString("cityid");
				String	string_AreaId= jsonObject2.getString("areaid");
				String	 string_Name = jsonObject2.getString("name");
				String	 string_CityName = jsonObject2.getString("cityname");
				String	 string_AreaName= jsonObject2.getString("areaname");
				String	 string_Adress= jsonObject2.getString("adress");
				String	 string_Telephone = jsonObject2.getString("tel");
				
				mOderIdList.add(string_AdressId);
				mNameList.add(string_Name);
				mCityNameList.add(string_CityName);
				mAreaNameList.add(string_AreaName);
				mAdressList.add(string_Adress);
				mTelephoneList.add(string_Telephone);
				
				mCityIdList.add(string_CityId);
				mAreaIdList.add(string_AreaId);
				}
				  //適配器初始化
				  initAdapter();
					
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
  private void initAdapter() {
	  adapter = new MyAdapter();
	mListView.setAdapter(adapter);
	}
class MyAdapter extends BaseAdapter{
	  @Override
		public View getView(final int position, View convertView, ViewGroup parent) {
		          if (position==mNameList.size()) {
		        	   View layout = getLayoutInflater().inflate(R.layout.item_myadress_lastitem, null);
		        	   TextView textView= (TextView) layout.findViewById(R.id.tv_lv_lastitem);
		        	   textView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
						//	Toast.makeText(MyadressActivity.this, "增加新地址", Toast.LENGTH_SHORT);
							startActivity(new Intent(MyadressActivity.this,NewAdressActivity.class));
							mApplication.setmString_actionbar3("增加新地址");
						}
					   });
		        	   return layout;
				}
		           else{
		        	   View layout = getLayoutInflater().inflate(R.layout.item_myadress, null);
		        	   Button ib_modify = (Button) layout.findViewById(R.id.bt_lv_myadress_modify);
		        	   Button ib_delete = (Button) layout.findViewById(R.id.bt_lv_myadress_delete);
		        	   
		        	   TextView  tv_Name = (TextView) layout.findViewById(R.id.tv_myadress_name);
		        	   TextView  tv_Tele = (TextView) layout.findViewById(R.id.tv_myadress_tele);
		        	   TextView  tv_CityName = (TextView) layout.findViewById(R.id.tv_myadress_cityname);
		        	   TextView  tv_AreaName = (TextView) layout.findViewById(R.id.tv_myadress_areaname);
		        	   TextView  tv_Adress = (TextView) layout.findViewById(R.id.tv_myadress_adress);
		        	   tv_Name.setText(mNameList.get(position)+" :");
		        	   tv_Tele.setText(mTelephoneList.get(position));
		        	   tv_CityName.setText(mCityNameList.get(position)+" :");
		        	   tv_AreaName.setText(mAreaNameList.get(position));
		        	   tv_Adress.setText(mAdressList.get(position));
		        	   
		        	   ib_modify.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent intent = new Intent(MyadressActivity.this,ModifyAdressActivity.class);
							mApplication.setmString_actionbar3("修改地址");
							intent.putExtra("name", mNameList.get(position));
							intent.putExtra("telephone", mTelephoneList.get(position));
							intent.putExtra("detail", mAdressList.get(position));
					     	intent.putExtra("cid", mCityIdList.get(position));
					    	intent.putExtra("areaid", mAreaIdList.get(position));
					    	intent.putExtra("aid", mOderIdList.get(position));
							startActivity(intent);
						}
					});
		        	   ib_delete.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								new AdressAsyTask().execute(Content.ADRESS_DELETE+"mid="+mEmber_ID+"&aid="+mOderIdList.get(position));
							}
						});
		        	   
		        	   return layout;
		           }
		           
		           
		}
	   class AdressAsyTask extends AsyncTask<String, Void, String>{  
			 
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					String string = jsonObject.getString("code");
					Log.e("AdressAsyTask", string);
					Toast.makeText(MyadressActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
					mOderIdList.clear();
					mNameList.clear();
					mCityNameList.clear();
					mAreaNameList.clear();
					mAdressList.clear();
					mTelephoneList.clear();
					downLoad();
					//	startActivity(new Intent(MyadressActivity.this,MyadressActivity.class));
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
	public int getCount() {
		// TODO Auto-generated method stub
		return mNameList.size()+1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	}

}
