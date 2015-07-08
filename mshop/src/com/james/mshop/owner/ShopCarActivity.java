package com.james.mshop.owner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.util.Bean;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopCarActivity extends Base3Activity implements OnClickListener{

	private ListView mListView;
	public ArrayList<String> mNameList=new ArrayList<String>();
	
	public ArrayList<String> mIDList=new ArrayList<String>();
	public ArrayList<String> mCurrent_IDList=new ArrayList<String>();
	
	public ArrayList<String> mPriceHk_1_List=new ArrayList<String>();
	public ArrayList<String> mPriceHk_2_List=new ArrayList<String>();
	public ArrayList<String> mPriceRMB_1_List=new ArrayList<String>();
	public ArrayList<String> mPriceRMB_2_List=new ArrayList<String>();
	public ArrayList<String> mQty=new ArrayList<String>();
	public ArrayList<String> mPicUrl_List=new ArrayList<String>();

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private TextView mTv_proNum;
	private TextView mTv_Shp;
	
	private String mString_hk_rmb;
	private UILApplication mApplication;
	private String mEmber_ID;
	public ShopCarActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_car);
		//獲得member_id
            mApplication = (UILApplication) getApplication();
			 mEmber_ID = mApplication.getmMember_Id();
			 Log.e("ShopCarActivity", "mEmber_ID"+mEmber_ID);
		//下載購物車信息
		downLoad();
		
	}
	private void findId() {
		mTv_Shp = (TextView) findViewById(R.id.tv_shop_account);
		mTv_proNum = (TextView) findViewById(R.id.tv_shop_pro_num);
		mListView = (ListView) findViewById(R.id.listView_settlement);
		findViewById(R.id.tv_shopcar_jiesuan).setOnClickListener(this);
	}
    private void downLoad() {
                  new ShopCarAsyTask().execute(Content.SHOP_LIST+mEmber_ID);    	
	}
	private void initAdapter() {
    	
    	MyAdapter adapter=new MyAdapter();
		mListView.setAdapter(adapter);
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
	class MyAdapter extends BaseAdapter{
    	@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
    		View layout = getLayoutInflater().inflate(R.layout.item_listview_settlement, null);
    		ImageView mImageView= (ImageView) layout.findViewById(R.id.iv_listview_shopcar);
    		TextView mTv_Name= (TextView) layout.findViewById(R.id.tv_listview_car_name);
    		TextView   mTv_HK1 = (TextView) layout.findViewById(R.id.tv_listview_car_hk1);
    		TextView   mTv_RMB1 = (TextView) layout.findViewById(R.id.tv_listview_car_rmb);
    		TextView mTv_Pty= (TextView) layout.findViewById(R.id.tv_listview_car_pty);
    		Button btn_Del = (Button) layout.findViewById(R.id.btn_shopcar_del);
    		Button btn_Plus= (Button) layout.findViewById(R.id.btn_shopcar_plus);
    		Button btn_Sub= (Button) layout.findViewById(R.id.btn_shopcar_sub);
    		     final int   int_qty = Integer.valueOf(mQty.get(position));
    		 btn_Del.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					new   AsyTask().execute(Content.SHOP_DELETE+"mid="+mEmber_ID+"&id="+mCurrent_IDList.get(position));    
				}
			});
    		 btn_Plus.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					new   AsyTask().execute("http://josie.i3.com.hk/mshop/json/shop_edit.php?" +
							"mid=" +mEmber_ID+
							"&id=" +mCurrent_IDList.get(position)+
							"&qty="+(int_qty+1));    
				}
			});
    		 btn_Sub.setOnClickListener(new OnClickListener() {
	
	            @Override
	          public void onClick(View v) {
		                        if(int_qty==1){
		        					new   AsyTask().execute(Content.SHOP_DELETE+"mid="+mEmber_ID+"&id="+mCurrent_IDList.get(position));    
		                        }else{
	            	new   AsyTask().execute("http://josie.i3.com.hk/mshop/json/shop_edit.php?" +
							"mid=" +mEmber_ID+
							"&id=" +mCurrent_IDList.get(position)+
							"&qty="+(int_qty-1));    
		                        }
	          }
            });
    		mTv_Pty.setText(mQty.get(position));
    		mTv_Name.setText(mNameList.get(position));
    		mTv_HK1.setText("HK$"+mPriceHk_1_List.get(position));
    		mTv_RMB1.setText("￥"+mPriceRMB_1_List.get(position));
    		  initImageLoaderOptions();
				imageLoader.displayImage(mPicUrl_List.get(position),
						mImageView, options);
			return layout;
		}
		@Override
		public int getCount() {
			return mPicUrl_List.size();
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
	
	//立即結算
	public void btn_setttlement() {
                startActivity(new Intent(this,ChoiceadressActivity.class));
                Bean.setAccount(mString_hk_rmb);
                 UILApplication application= (UILApplication) getApplication();
                 application.setmString_actionbar3("選擇地址");
	}
	
	
class ShopCarAsyTask extends AsyncTask<String, Void, String>{  
				                        private double price_hk=0;
				                        private double price_rmb=0;
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					JSONObject jsonObject;
					try {
						
						jsonObject = new JSONObject(result);
					String code= jsonObject.getString("code");
					if ("0".equals(code)) {
						mApplication.setNum_Shopcar(0);
						View layout = findViewById(R.id.rela_shopcar_nothing);
						layout.setVisibility(View.VISIBLE);
						TextView tv_nothing= (TextView) layout.findViewById(R.id.tv_shopcar_nothing);
						tv_nothing.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								
								startActivity(new Intent(ShopCarActivity.this,IndexActivity.class));
								finish();
							}
						});
					}
					else {
						findViewById(R.id.rela_shopcar).setVisibility(View.VISIBLE);
						findId();
						JSONArray array = jsonObject.getJSONArray("data");
						StringBuilder string_hk=new StringBuilder();
						  for (int i = 0; i < array.length(); i++) {
							 JSONObject jsonObject2 = array.getJSONObject(i);
							 String	string_pid= jsonObject2.getString("pid");
							 String	string_id= jsonObject2.getString("id");
				    			String	 string_Name = jsonObject2.getString("name");
				    			String	 string_Pricehk1 = jsonObject2.getString("pricehk1");
				    			String	 string_Pricehk2 = jsonObject2.getString("pricehk2");
				    			String	 string_Pricermb1 = jsonObject2.getString("pricermb1");
				    			String	 string_Pricermb2 = jsonObject2.getString("pricermb2");
				                String     string_PicUrl = jsonObject2.getString("pic"); 
				                String     string_Qty = jsonObject2.getString("qty"); 
				                double price_hk1 = Double.valueOf(string_Pricehk1);
				                int qty = Integer.valueOf(string_Qty);
				                price_hk+=price_hk1*qty;
				                double price_rmb1 = Double.valueOf(string_Pricermb1);
				                price_rmb+=price_rmb1*qty;
				                mCurrent_IDList.add(string_id);
				                mNameList.add(string_Name);
				    			mIDList.add(string_pid);
				    			mPriceHk_1_List.add(string_Pricehk1);
				    			mPriceHk_2_List.add(string_Pricehk2);
				    			mPriceRMB_1_List.add(string_Pricermb1);
				    			mPriceRMB_2_List.add(string_Pricermb2);   
				    			mQty.add(string_Qty);
				    			Log.e("ShopCar", string_Qty);
				    			mPicUrl_List.add(string_PicUrl);
						}
						  //計算總商品件數
						  int  mCarNum=0;
						  for (int i = 0; i < mQty.size(); i++) {
							              int num = Integer.valueOf(mQty.get(i)) ;
							              mCarNum+=num;
						}
						  mApplication.setNum_Shopcar(mCarNum);						  
						  //計算總金額
						  mString_hk_rmb= "HK$ "+price_hk+"  ￥"+price_rmb;
						mTv_Shp.setText(mString_hk_rmb);
						  mTv_proNum.setText(mCarNum+"件");
						  findViewById(R.id.linearLayout1).setVisibility(View.VISIBLE);
						  findViewById(R.id.mPBShopCar).setVisibility(View.GONE);
						  //設置適配器
							initAdapter();
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
			class AsyTask extends AsyncTask<String, Void, String>{  
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						String string = jsonObject.getString("code");
						startActivity(new Intent(ShopCarActivity.this,ShopCarActivity.class));
						finish();
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

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_shopcar_jiesuan:
					btn_setttlement();
					break;

				default:
					break;
				}
				
			}
}
