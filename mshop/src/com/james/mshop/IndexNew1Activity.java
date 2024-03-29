package com.james.mshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;

import com.james.mshop.IndexNew1Activity;
import com.james.mshop.LoginActivity;
import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.product.LatestActivity;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.lidroid.xutils.util.OtherUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.webdesign688.mshop.R;

public class IndexNew1Activity extends Base3Activity  {

	
	public IndexNew1Activity() {
		super(R.string.hello_world);
	}
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	
	private GridView mGridView;
	private String m_Thid;
	public ArrayList<String> mNameList=new ArrayList<String>();
	public ArrayList<String> mIDList=new ArrayList<String>();
	public ArrayList<String> mPriceHk_1_List=new ArrayList<String>();
	public ArrayList<String> mPriceHk_2_List=new ArrayList<String>();
	public ArrayList<String> mPriceRMB_1_List=new ArrayList<String>();
	public ArrayList<String> mPriceRMB_2_List=new ArrayList<String>();
	public ArrayList<String> mPicUrl_List=new ArrayList<String>();
	public ArrayList<String> mQty=new ArrayList<String>();
	
	private UILApplication application;
	private String m_Oid;
	private String m_Tid;
	private Spinner mSpinner;
	private String[] mStringList=new String[]{
			"排序：默認",
			"排序：最新上架","排序：人氣之選",
			"排序：隨機之選","排序：價格向上",
			"排序：價格向下"
	};
	private TextView mTv_Title;
	private boolean hasPressedBack;
	private Handler mHandler=new Handler();
	private String mString_Research;
	private String mEmber_ID;
	private int ST;
	private String keyword ="";
	private int ot=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		ST =getIntent().getIntExtra("OT", 1);

		mGridView = (GridView) findViewById(R.id.gridView_latest);
		mSpinner = (Spinner) findViewById(R.id.spinner_latest);
		//mTv_Title = (TextView) findViewById(R.id.tv_latest_title);
		application = (UILApplication) getApplication();
		//初始化Spinner
		initSpinner();
		mSpinner.setSelection(1);
		//點擊Item
		itemClick();
		 //下載信息
	//	downLoad();
		//下載購物車商品總數量
		boolean loginTag = application.getLoginTag();
		if (loginTag) {
			 mEmber_ID = application.getmMember_Id();
		getCarNum();
		}
	}
	private void getCarNum() {
		 new CarNumAscTask().execute(Content.SHOP_LIST+mEmber_ID);    	
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

	public 	 void btn_brush(View v) {
	      
            Intent intent =new Intent(IndexNew1Activity.this,BrushActivity.class);
            intent.putExtra("ST", ST+"");
            application.setmString_actionbar3("刷選");
            startActivityForResult(intent, 1);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1&&resultCode==2){
			keyword =data.getStringExtra("keyword");
			 findViewById(R.id.progressBar_latest).setVisibility(View.VISIBLE);
			  mGridView.setVisibility(View.GONE);

			new DownLoadAsyTask().execute(Content.CATEGORY_List+
					"oid=" +
					"&st="+ST +
					"&ot="+ot+"&keyword="+keyword);

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void itemClick() {
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
					mNameList.clear();
	    			mIDList.clear();
	    			mPriceHk_1_List.clear();                                             
	    			mPriceHk_2_List.clear();
	    			mPriceRMB_1_List.clear();
	    			mPriceRMB_2_List.clear();  			
	    			mPicUrl_List.clear();
					switch (position) {
					case 0:
						ot=0;
						break; 
					case 1:
						ot=1;
						break;    
					case 2:
						ot=2;
						break;    
					case 3:
						ot=3;
						break;    
					case 4:
						ot=4;
						break; 
					case 5:
						ot=5;
						break;
					default:
						break;
					}
					new DownLoadAsyTask().execute(Content.CATEGORY_List+
							"oid=" +
							"&st="+ST +
							"&ot="+ot+"&keyword="+keyword);
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
	private void initSpinner() {
         ArrayAdapter<String> mListSpinner=new ArrayAdapter<String>(this	, R.layout.item_spinner, mStringList);	
         mSpinner.setAdapter(mListSpinner);
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
    	new DownLoadAsyTask().execute(Content.CATEGORY_List+
    			"oid=" +	
    			"&st=0" 
    			);
	}
    class DownLoadAsyTask extends AsyncTask<String, Void, String>{  
   	 
    	@Override
    	protected void onPostExecute(String result) {
    		super.onPostExecute(result);
    		JSONObject jsonObject;
    		try {
    			jsonObject = new JSONObject(result);
    			
    			String code =jsonObject.getString("code");

    			if(!TextUtils.isEmpty(code)&&code.equals("1")){
    			JSONArray array = jsonObject.getJSONArray("data");
    			
    			  for (int i = 0; i < array.length(); i++) {
    				 JSONObject jsonObject2 = array.getJSONObject(i);
    			String	string_Id= jsonObject2.getString("id");
    			String	 string_Name = jsonObject2.getString("name");
    			String	 string_Pricehk1 = jsonObject2.getString("pricehk1");
    			String	 string_Pricehk2 = jsonObject2.getString("pricehk2");
    			String	 string_Pricermb1 = jsonObject2.getString("pricermb1");
    			String	 string_Pricermb2 = jsonObject2.getString("pricermb2");
                String     string_PicUrl = jsonObject2.getString("pic");    			    
    			
    			mNameList.add(string_Name);
    			mIDList.add(string_Id);
    			mPriceHk_1_List.add(string_Pricehk1);
    			mPriceHk_2_List.add(string_Pricehk2);
    			mPriceRMB_1_List.add(string_Pricermb1);
    			mPriceRMB_2_List.add(string_Pricermb2);    			
    			mPicUrl_List.add(string_PicUrl);
    				 Log.e("DownLoadAsyTask", ""+string_Id);
    				 Log.e("DownLoadAsyTask", ""+string_Name);
    				 Log.e("DownLoadAsyTask", ""+string_Pricehk1);
    				 Log.e("DownLoadAsyTask", ""+string_Pricehk2);
    				 Log.e("DownLoadAsyTask", ""+string_Pricermb1);
    				 Log.e("DownLoadAsyTask", ""+string_Pricermb2);
    				 Log.e("DownLoadAsyTask", ""+string_PicUrl);
    				 
    			}
    			  findViewById(R.id.progressBar_latest).setVisibility(View.GONE);
    			  
    			  mGridView= (GridView) findViewById(R.id.gridView_latest);
    			  mGridView.setVisibility(View.VISIBLE);
    			  selectItem();
    				//初始化適配器
    				initAdapter();
    			}else if(code.equals("0")){
					mNameList.clear();
	    			mIDList.clear();
	    			mPriceHk_1_List.clear();                                             
	    			mPriceHk_2_List.clear();
	    			mPriceRMB_1_List.clear();
	    			mPriceRMB_2_List.clear();  			
	    			mPicUrl_List.clear();
	    			  findViewById(R.id.progressBar_latest).setVisibility(View.GONE);

	    			String msg =jsonObject.getString("msg");

	    			Toast.makeText(getApplicationContext(), msg, 0).show();
    			}else{
    				showDialog(2);
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
	private void selectItem() {
    	mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(IndexNew1Activity.this,Tab1Activity.class);
				intent.putExtra("name", mNameList.get(position));
				application.setmString_actionbar3(mNameList.get(position));
				intent.putExtra("id", mIDList.get(position));
				application.setProduct_id(mIDList.get(position));
				startActivity(intent);		
				}
			
		});
	}
	private void initAdapter() {
           mGridView.setAdapter(new MyAdapter());		
	}
	class MyAdapter extends BaseAdapter{
    	@Override
		public View getView(int position, View convertView, ViewGroup parent) {  
    		
    		View layout = getLayoutInflater().inflate(R.layout.item_gridview_latest, null);
    		ImageView mImageView= (ImageView) layout.findViewById(R.id.imageView_latest);
    		TextView tv_Name= (TextView) layout.findViewById(R.id.tv_latest_name);
    		TextView tv_Prick_HK= (TextView) layout.findViewById(R.id.tv_latest_price_hk);
    		TextView tv_Prick_RMB= (TextView) layout.findViewById(R.id.tv_latest_price_rmb);
    		  initImageLoaderOptions();
				imageLoader.displayImage(mPicUrl_List.get(position),
						mImageView, options);
    		tv_Name.setText(mNameList.get(position));
    		tv_Prick_HK.setText("HK$ "+mPriceHk_1_List.get(position));
    		tv_Prick_RMB.setText("￥"+mPriceRMB_1_List.get(position));
    		
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
			
			@Override
			@Deprecated
			protected Dialog onCreateDialog(int id, Bundle args) {
				if (id==1) {
					return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("請輸入搜索內容").setPositiveButton("確定", null).create();
				}
				else {
					
					return new AlertDialog.Builder(this).setTitle(R.string.tip).
							setMessage(R.string.no_internet).setPositiveButton("確定", null).create();
				}
			}
			@Override
			public void finish() {
				 ShareSDK.stopSDK(this);
				super.finish();
			}

}
