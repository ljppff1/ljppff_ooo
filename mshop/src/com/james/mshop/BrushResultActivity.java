package com.james.mshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.owner.ShopCarActivity;
import com.james.mshop.product.LatestActivity;
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
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import com.webdesign688.mshop.R;

public class BrushResultActivity extends Base3Activity {
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
	private String m_Keyword;
	public BrushResultActivity() {
		super(R.string.hello_world);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brush_result);
		mGridView = (GridView) findViewById(R.id.gridView_latest);
		mSpinner = (Spinner) findViewById(R.id.spinner_latest);
		//mTv_Title = (TextView) findViewById(R.id.tv_latest_title);
		application = (UILApplication) getApplication();
		//得到thid
		Intent intent = getIntent();
		m_Keyword = intent.getStringExtra("keyword");
		/*//點擊Item
		itemClick();*/
		 //下載信息
		downLoad();
		//初始化Spinner
	}
	private void itemClick() {
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int ot=0;
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
							"oid=" +m_Oid+
							"&tid=" +m_Tid+
							"&thid=" +m_Thid+
							"&st=0" +
							"&ot="+ot);
				
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
    	new DownLoadAsyTask().execute(Content.CATEGORY_List+"st=1" +
    			"&keyword="+m_Keyword);
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
				
				Intent intent = new Intent(BrushResultActivity.this,Tab1Activity.class);
				intent.putExtra("name", mNameList.get(position));
				application.setmString_actionbar3(mNameList.get(position));
				intent.putExtra("id", mIDList.get(position));
				application.setProduct_id(mIDList.get(position));
				
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
			//購物車
			public void btn_shoppingcar(View v) {
				UILApplication application = (UILApplication) getApplication();
				boolean loginTag = application.getLoginTag();
				if (loginTag) {
					startActivity(new Intent(this,ShopCarActivity.class));
				}
				else {
					startActivity(new Intent(this,LoginActivity.class));
				}
			}
			@Deprecated
			protected Dialog onCreateDialog(int id) {
				// TODO Auto-generated method stub
				return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
			}

}
