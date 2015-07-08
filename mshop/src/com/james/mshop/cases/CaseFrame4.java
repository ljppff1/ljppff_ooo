package com.james.mshop.cases;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.OwnerOrderActivity;
import com.james.mshop.OwnerOrderActivity1;
import com.webdesign688.mshop.R;
import com.james.mshop.cases.Case1Activity.AsyTask;
import com.james.mshop.cases.Case1Activity.Myadapter;
import com.james.mshop.cases.Case1Activity.OrderListAsyTask;
import com.james.mshop.owner.ChoiceadressActivity;
import com.james.mshop.pay.PayPalActivity;
import com.james.mshop.util.Bean;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;





public class CaseFrame4 extends Fragment 
{  
	
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
private View mLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mLayout = inflater.inflate(R.layout.activity_case2, null);
		//獲得member_id
				UILApplication application = (UILApplication) getActivity().getApplication();
				mEmber_ID = application.getmMember_Id();
				Log.e("Case1Activity", mEmber_ID);
				//下載訂單信息
				downLoad();
		return mLayout; 
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
	}
	 private void selectItem() {
	    	mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
	                Intent intent=new Intent(getActivity(),OwnerOrderActivity1.class);
	                Bean.setAccount("HK$ "+mHKList.get(arg2)+"  ￥"+mRmbList.get(arg2));

	                intent.putExtra("id", mIdList.get(arg2));
	                intent.putExtra("time", mTimeList.get(arg2));
					startActivity(intent);				
				}
			});
		}
		private void downLoad() {
	    	new OrderListAsyTask().execute("http://josie.i3.com.hk/mshop/json/m_orderlist.php?mid=" +mEmber_ID+
	    			"&st=3");
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
				View layout = getActivity().getLayoutInflater().inflate(R.layout.item_listview_case2, null);
				
				TextView  tv_Time = (TextView) layout.findViewById(R.id.tv_listview_case1_time);
				TextView  tv_Name = (TextView) layout.findViewById(R.id.tv_listview_case1_name);
				TextView  tv_HK = (TextView) layout.findViewById(R.id.tv_listview_case1_hk);
				TextView  tv_RMB = (TextView) layout.findViewById(R.id.tv_listview_case1_rmb);
				TextView  tv_Color = (TextView) layout.findViewById(R.id.tv_listview_case1_color);
				TextView  tv_Size = (TextView) layout.findViewById(R.id.tv_listview_case1_size);
				TextView  tv_Total = (TextView) layout.findViewById(R.id.tv_listview_case1_total);
				
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
						  mLayout.findViewById(R.id.listView_case2).setVisibility(View.VISIBLE);
						  mListView = (ListView) mLayout.findViewById(R.id.listView_case2);
						//ListView 點擊
						selectItem();
						initAdapter();
						mLayout.findViewById(R.id.mPbcaseframe2).setVisibility(View.GONE);

					}
					else {
						mLayout.findViewById(R.id.mPbcaseframe2).setVisibility(View.GONE);

						mLayout.findViewById(R.id.rela_nocse2).setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
				}
				}
				@Override
				protected String doInBackground(String... params) {
					String str=params[0];
					return getJson.getData(str);
				}
				}
	
}
