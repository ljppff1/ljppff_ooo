package com.james.mshop.cases;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.OwnerOrderActivity;
import com.james.mshop.pay.OrderConfirmActivity;
import com.james.mshop.pay.PayPalActivity;
import com.james.mshop.util.Bean;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.webdesign688.mshop.R;

public class Case1Activity extends Base3Activity {

	private ListView mListView;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ArrayList<String>    mIdList=new ArrayList<String>();
	private ArrayList<String>    mNameList=new ArrayList<String>();
	private ArrayList<String>    mTimeList=new ArrayList<String>();
	private ArrayList<String>    mImageUrlList=new ArrayList<String>();
	private ArrayList<String>    mColorList=new ArrayList<String>();
	private ArrayList<String>    mSizeList=new ArrayList<String>();
	private ArrayList<String>    mTotalHKList=new ArrayList<String>();
	private ArrayList<String>    mTotalRmbList=new ArrayList<String>();
	private ArrayList<String>    mHKList=new ArrayList<String>();
	private ArrayList<String>    mRmbList=new ArrayList<String>();
	private String mEmber_ID;
    public Case1Activity(){
    	super(R.string.hello_world);
    }
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case1);
		
		//獲得member_id
		UILApplication application = (UILApplication) getApplication();
		mEmber_ID = application.getmMember_Id();
		Log.e("Case1Activity", mEmber_ID);
		//下載訂單信息
		downLoad();
	}
    private void selectItem() {
		
    	mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                Intent intent=new Intent(Case1Activity.this,OwnerOrderActivity.class);
                intent.putExtra("id", mIdList.get(arg2));
                intent.putExtra("time", mTimeList.get(arg2));
               
               UILApplication application= (UILApplication) getApplication();
               application.setmString_actionbar3("訂單詳情");
                
				startActivity(intent);				
			}
		});
    	
	}
	private void downLoad() {
		
    	new OrderListAsyTask().execute("http://josie.i3.com.hk/mshop/json/m_orderlist.php?mid=" +mEmber_ID+
    			"&st=0");
    	
	}
  //UIL
  		private void initImageLoaderOptions() {
  			options = new DisplayImageOptions.Builder()
  					.showImageForEmptyUri(R.drawable.ic_empty)
  					.cacheInMemory()
  					.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
  					.bitmapConfig(Bitmap.Config.RGB_565).build();
  		}
	private void initAdapter() {

		mListView.setAdapter(new Myadapter());
		
	}
	class Myadapter extends BaseAdapter{

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View layout = getLayoutInflater().inflate(R.layout.item_listview_case1, null);
			
			
			TextView  tv_Time = (TextView) layout.findViewById(R.id.tv_listview_case1_time);
			TextView  tv_Name = (TextView) layout.findViewById(R.id.tv_listview_case1_name);
			TextView  tv_HK = (TextView) layout.findViewById(R.id.tv_listview_case1_hk);
			TextView  tv_RMB = (TextView) layout.findViewById(R.id.tv_listview_case1_rmb);
			TextView  tv_Color = (TextView) layout.findViewById(R.id.tv_listview_case1_color);
			TextView  tv_Size = (TextView) layout.findViewById(R.id.tv_listview_case1_size);
			TextView  tv_Total = (TextView) layout.findViewById(R.id.tv_listview_case1_total);
			Button  btn_Delete = (Button) layout.findViewById(R.id.btn_listview_case1_delete);
			Button  btn_Payfor = (Button) layout.findViewById(R.id.btn_listview_case1_payfor);
			
			ImageView imageView = (ImageView) layout.findViewById(R.id.iv_listview_case1_image);
			//設置內容
			tv_Time.setText(mTimeList.get(position));
			tv_Name.setText(mNameList.get(position));
			tv_HK.setText("HK$"+mHKList.get(position));
			tv_RMB.setText("￥"+mRmbList.get(position));
			tv_Color.setText(mColorList.get(position));
			tv_Size.setText(mSizeList.get(position));
			tv_Total.setText("HK$ "+mTotalHKList.get(position)+"  ￥"+mTotalRmbList.get(position));
			 initImageLoaderOptions();
				imageLoader.displayImage(mImageUrlList.get(position),
						imageView, options);
				
				//刪除和付款
				btn_Delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//取消訂單
					new AsyTask().execute("http://josie.i3.com.hk/mshop/json/m_ordercancel.php?mid=" +mEmber_ID+
							"&id="+mIdList.get(position));
					downLoad();
					
					}
				});
				btn_Payfor.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(Case1Activity.this,PayPalActivity.class);
						startActivity(intent);
					}
				});
			return layout;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
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
	class OrderListAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String code= jsonObject.getString("code");
				if ("1".equals(code)) {
					
					JSONArray array = jsonObject.getJSONArray("data");
					  for (int i = 0; i < array.length(); i++) {
						 JSONObject jsonObject2 = array.getJSONObject(i);
					String	string_Id= jsonObject2.getString("id");
					String	 string_Time = jsonObject2.getString("time");
					String	 string_Name= jsonObject2.getString("name");
					String	 string_PicUrl= jsonObject2.getString("pic");
					String	 string_PriceHK= jsonObject2.getString("pricehk");
					String	 string_PriceRmb= jsonObject2.getString("pricermb");
					String	 string_TotalHk= jsonObject2.getString("totalhk");
					String	 string_TotalRmb= jsonObject2.getString("totalrmb");
					String	 string_Color= jsonObject2.getString("color");
					String	 string_Size= jsonObject2.getString("size");
					Log.e("Case1Activity", string_Id);
					Log.e("Case1Activity", string_Time);
					Log.e("Case1Activity", string_Name);
					Log.e("Case1Activity", string_PicUrl);
					Log.e("Case1Activity", string_PriceHK);
					Log.e("Case1Activity", string_TotalRmb);
					mIdList.add(string_Id);
					mTimeList.add(string_Time);
					mNameList.add(string_Name);
					mImageUrlList.add(string_PicUrl);
					mSizeList.add(string_Size);
					mColorList.add(string_Color);
					mHKList.add(string_PriceHK);
					mRmbList.add(string_PriceRmb);
					mTotalHKList.add(string_TotalHk);
					mTotalRmbList.add(string_TotalRmb);
													
					}
					  //初始化適配器
					  findViewById(R.id.listView_case1).setVisibility(View.VISIBLE);
					  mListView = (ListView) findViewById(R.id.listView_case1);
					//ListView 點擊
					selectItem();
					initAdapter();
				}
				else {
					findViewById(R.id.rela_nocse).setVisibility(View.VISIBLE);
				}
			} catch (JSONException e) {
				showDialog(2);
			}
			}
			@Override
			protected String doInBackground(String... params) {
				String str=params[0];
				return getJson.getData(str);
			}
			}
	 class AsyTask extends AsyncTask<String, Void, String>{  
		 
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(result);
					String string = jsonObject.getString("code");
						
						mIdList.clear();
						mTimeList.clear();
						mNameList.clear();
						mImageUrlList.clear();
						mSizeList.clear();
						mColorList.clear();
						mHKList.clear();
						mRmbList.clear();
						mTotalHKList.clear();
						mTotalRmbList.clear();
						Toast.makeText(Case1Activity.this, "訂單已取消", Toast.LENGTH_SHORT).show();
						downLoad();
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
