package com.james.mshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.cases.Case1Activity;
import com.james.mshop.owner.CaseManagerActivity;
import com.james.mshop.pay.PayPalActivity;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.webdesign688.mshop.R;

public class OwnerOrderActivity extends Base3Activity {

	private String mString_Id;
	private ListView mListView;
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

	private String string_Amounthk;
	private String string_Amountrmb;
	private String string_Shiphk;
	private String string_Shiprmb;
	private String string_Totalhk;
	private String string_Totalrmb;
	private String string_no;

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private String mMember_id;
	public OwnerOrderActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner_order);
		//獲得member_id
		UILApplication application = (UILApplication) getApplication();
		mMember_id = application.getmMember_Id();
		Log.e("OwnerOrderActivity", "mMember_id"+mMember_id);
		mListView = (ListView) findViewById(R.id.listView_own_order);
		TextView  tv_Time = (TextView) findViewById(R.id.tv_owner_order_time);
		//獲得訂單ID
		Intent intent = getIntent();
		mString_Id = intent.getStringExtra("id");
		String string_Time = intent.getStringExtra("time");
		tv_Time.setText(string_Time);
		//下載信息
		downLoad();
		  //初始化適配器
		//  initAdapter();
	}
	//UIL
		private void initImageLoaderOptions() {
			options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.ic_empty)
					.cacheInMemory()
					.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
   private void downLoad() {
	   new OrderListAsyTask().execute("http://josie.i3.com.hk/mshop/json/m_orderdetail.php?mid="+mMember_id+"&id="+mString_Id);	
	}
class MyAdapter extends BaseAdapter{
	   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
				
				   View layout = getLayoutInflater().inflate(R.layout.item_listview_ownorder, null);
				   TextView tv_Name= (TextView) layout.findViewById(R.id.tv_listview_ownorder_name);
				   TextView tv_Color= (TextView) layout.findViewById(R.id.tv_listview_ownorder_color);
				   TextView tv_Hk= (TextView) layout.findViewById(R.id.tv_listview_ownorder_hk);
				   TextView tv_RMB= (TextView) layout.findViewById(R.id.tv_listview_ownorder_rmb);
				   TextView tv_Size= (TextView) layout.findViewById(R.id.tv_listview_ownorder_size);
				   ImageView imageView=(ImageView) findViewById(R.id.iv_listview_ownorder_image);
				   Log.e("position", ""+position);
				   Log.e("OwnerOrder", mImageUrlList.get(position));
				   Log.e("OwnerOrder", mColorList.get(position));
			  /* initImageLoaderOptions();
				imageLoader.displayImage("http://josie.i3.com.hk/mshop/UPFILE/ProductPic/small/201422414263115.jpg",
						imageView, options);*/
				
			   tv_Name.setText(mNameList.get(position));
			   tv_Color.setText(mColorList.get(position));
			   tv_Hk.setText(mHKList.get(position));
			   tv_RMB.setText(mRmbList.get(position));
			   tv_Size.setText(mSizeList.get(position));
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
   /*class Myadapter extends BaseAdapter{
	   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
		   View layout = getLayoutInflater().inflate(R.layout.item_listview_ownorder, null);
		   Log.e("OwnerOrder", mImageUrlList.get(position));
		   Log.e("OwnerOrder", mColorList.get(position));
			return layout;
		}
	@Override
	public int getCount() {
		Log.e("Myadapter", ""+mImageUrlList.size());
		return mImageUrlList.size();
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

	}*/
class OrderListAsyTask extends AsyncTask<String, Void, String>{  
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JSONObject jsonObject;
		try {
			
			jsonObject = new JSONObject(result);
			JSONObject jsonObject3 = jsonObject.getJSONObject("data");
			string_Amounthk = jsonObject3.getString("amounthk");
			string_Amountrmb = jsonObject3.getString("amountrmb");
			string_Shiphk = jsonObject3.getString("shiphk");
			string_Shiprmb = jsonObject3.getString("shiprmb");
			string_Totalhk = jsonObject3.getString("totalhk");
			string_Totalrmb = jsonObject3.getString("totalrmb");
			 string_no= jsonObject3.getString("no");

			JSONArray array = jsonObject3.getJSONArray("product");

			  for (int i = 0; i < array.length(); i++) {
				 JSONObject jsonObject2 = array.getJSONObject(i);
			String	 string_Name= jsonObject2.getString("name");
			String	 string_PicUrl= jsonObject2.getString("pic");
			String	 string_PriceHK= jsonObject2.getString("pricehk");
			String	 string_PriceRmb= jsonObject2.getString("pricermb");
			String	 string_Color= jsonObject2.getString("color");
			String	 string_Size= jsonObject2.getString("size");

			mNameList.add(string_Name);
			mImageUrlList.add(string_PicUrl);
			mSizeList.add(string_Size);
			mColorList.add(string_Color);
			mHKList.add(string_PriceHK);
			mRmbList.add(string_PriceRmb);
					
			}

			  initAdapter();
			  
		} catch (JSONException e) {
			
		}
		}
		@Override
		protected String doInBackground(String... params) {
			String str=params[0];
			return getJson.getData(str);
		}
		}
private void initAdapter() {
	
	 View layout = getLayoutInflater().inflate(R.layout.item_listview_ownorder_lastitem, null);
	   TextView tv_ProductAccount= (TextView) layout.findViewById(R.id.tv_listview_ownorder_productaccount);
	   TextView tv_TotalAccount= (TextView) layout.findViewById(R.id.tv_listview_ownorder_totalaccount);
	   TextView tv_Shipment= (TextView) layout.findViewById(R.id.tv_listview_ownorder_shipment);
	   Button btn_Cancel= (Button) layout.findViewById(R.id.btn_listview_ownorder_delete);
	   Button btn_Payfor= (Button) layout.findViewById(R.id.btn_listview_ownorder_payfor);
	   
	   btn_Cancel.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//取消訂單
			new AsyTask().execute("http://josie.i3.com.hk/mshop/json/m_ordercancel.php?mid="+mMember_id+
					"&id="+mString_Id);
			downLoad();
		}
	   });
	   btn_Payfor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent intent=new Intent(OwnerOrderActivity.this,PayPalActivity.class);
                intent.putExtra("NAME", mNameList.get(0));
                intent.putExtra("HK", mHKList.get(0));
                intent.putExtra("ID",string_no);
            startActivity(intent);

			}
		   });
	   tv_ProductAccount.setText( "HK$ "+string_Amounthk+"  ￥"+string_Amountrmb);
	   tv_TotalAccount.setText( "HK$ "+string_Totalhk+"  ￥"+string_Totalrmb);
	   tv_Shipment.setText( "HK$ "+string_Shiphk+"  ￥"+string_Shiprmb);
	   
	mListView.addFooterView(layout);
	 mListView.setAdapter(new MyAdapter());	
	 Log.e("initAdapter", ""+mImageUrlList.size());
	
}
class AsyTask extends AsyncTask<String, Void, String>{  
	 
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String string = jsonObject.getString("code");
	    	Toast.makeText(OwnerOrderActivity.this, "訂單已取消", Toast.LENGTH_SHORT).show();
	    	startActivity(new Intent(OwnerOrderActivity.this,CaseManagerActivity.class));
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
@Deprecated
protected Dialog onCreateDialog(int id) {
	// TODO Auto-generated method stub
	return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
}

}
