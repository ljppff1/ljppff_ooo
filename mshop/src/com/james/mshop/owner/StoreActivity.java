package com.james.mshop.owner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.Tab1Activity;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreActivity extends Base3Activity {
	public ArrayList<String> mPriceHk_1_List=new ArrayList<String>();
	public ArrayList<String> mPriceHk_2_List=new ArrayList<String>();
	public ArrayList<String> mPriceRMB_1_List=new ArrayList<String>();
	public ArrayList<String> mPriceRMB_2_List=new ArrayList<String>();
	public ArrayList<String> mNameList=new ArrayList<String>();
	public ArrayList<String> mIDList=new ArrayList<String>();
	public ArrayList<String> mPidList=new ArrayList<String>();
	
	public ArrayList<String> mPicUrlList=new ArrayList<String>();
	private ListView mListView;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private String mEmber_ID;
	private UILApplication application;
	public  StoreActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store);
		application = (UILApplication) getApplication();
	       mEmber_ID = application.getmMember_Id();
	  	 Log.e("StoreActivity", "mEmber_ID"+mEmber_ID);
		
		downLoad();
	}
	//UIL
	private void initImageLoaderOptions() {
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	private void downLoad() {
		new DownLoadAsyTask().execute(Content.FAVORITELIST+mEmber_ID);
	}
	class DownLoadAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				
				jsonObject = new JSONObject(result);
				String code = jsonObject.getString("code");
				if ("1".equals(code)) {
					JSONArray array = jsonObject.getJSONArray("data");
					  for (int i = 0; i < array.length(); i++) {
						 JSONObject jsonObject2 = array.getJSONObject(i);
					String	string_Id= jsonObject2.getString("fid");
					String	string_Pid= jsonObject2.getString("pid");
					String	 string_Name = jsonObject2.getString("name");
	    			String	 string_Pricehk1 = jsonObject2.getString("pricehk1");
	    			String	 string_Pricehk2 = jsonObject2.getString("pricehk2");
	    			String	 string_Pricermb1 = jsonObject2.getString("pricermb1");
	    			String	 string_Pricermb2 = jsonObject2.getString("pricermb2");
	    			
	    			String	 string_PicUrl = jsonObject2.getString("pic");
	    			
	    			mPriceHk_1_List.add(string_Pricehk1);
	    			mPriceHk_2_List.add(string_Pricehk2);
	    			mPriceRMB_1_List.add(string_Pricermb1);
	    			mPriceRMB_2_List.add(string_Pricermb2);
	    			mPicUrlList.add(string_PicUrl);
					mNameList.add(string_Name);
					mIDList.add(string_Id);
					mPidList.add(string_Pid);
					}
					  findViewById(R.id.progressBar_store).setVisibility(View.GONE);
					  mListView = (ListView) findViewById(R.id.listView_store);
					  mListView.setVisibility(View.VISIBLE);
					  
					  //適配器初始化
					  initAdapter();
					  //點擊ListView
					  selectListView();
				}
				else {
					findViewById(R.id.progressBar_store).setVisibility(View.GONE);
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
	private void selectListView() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
                          String string_pid = mPidList.get(position);				
                          Intent intent=new Intent(StoreActivity.this,Tab1Activity.class);
                          application.setProduct_id(string_pid);
                          application.setmString_actionbar3(mNameList.get(position));
						startActivity(intent);
			}
			
		});
		
	}
	private void initAdapter() {
		mListView.setAdapter(new MyAdapter());		
	}
			class MyAdapter extends BaseAdapter{
				@Override
				public View getView(final int position, View convertView,
						ViewGroup parent) {
					View layout = getLayoutInflater().inflate(R.layout.item_listview_store, null);
					ImageView imageView = (ImageView) layout.findViewById(R.id.iv_listview_store);
					TextView tv_Name= (TextView) layout.findViewById(R.id.tv_listview_store);
					TextView tv_PriceHk= (TextView) layout.findViewById(R.id.tv_listview_pricehk);
					TextView tv_PriceRMB= (TextView) layout.findViewById(R.id.tv_listview_pricermb);
					
					tv_Name.setText(mNameList.get(position));
					tv_PriceHk.setText("HK$ "+mPriceHk_1_List.get(position));
					tv_PriceRMB.setText("￥ "+mPriceRMB_1_List.get(position));
					
					 initImageLoaderOptions();
						imageLoader.displayImage(mPicUrlList.get(position),
								imageView, options);
					
					          Button mButton= (Button) layout.findViewById(R.id.button_listview_store);
					          mButton.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Log.e("mButton", mIDList.get(position));
                             			new DeleteAsyTask().execute(Content.FAVORITE_DELETE+"mid="+mEmber_ID+"&fid="+mIDList.get(position));	
                             			
								}
							});
					return layout;
				}
				@Override
				public int getCount() {
					return mNameList.size();
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
			class DeleteAsyTask extends AsyncTask<String, Void, String>{  
				 
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						String string = jsonObject.getString("code");
						Log.e("DeleteAsyTask", string);
						//如果code為1,則刪除成功，更新ListView
						Toast.makeText(StoreActivity.this, "刪除成功", Toast.LENGTH_SHORT).show();
						if ("1".equals(string)) {
							
							mPriceHk_1_List.clear();
			    			mPriceHk_2_List.clear();
			    			mPriceRMB_1_List.clear();
			    			mPriceRMB_2_List.clear();
			    			mPicUrlList.clear();
							mNameList.clear();
							mIDList.clear();
							downLoad();
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
						return new AlertDialog.Builder(this).setTitle(R.string.tip)
								.setMessage("無收藏的商品").setPositiveButton(R.string.confirm, null).create();
					}
					else {
						
						return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
					}
			     }

}
