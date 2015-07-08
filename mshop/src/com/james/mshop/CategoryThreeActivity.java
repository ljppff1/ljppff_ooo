package com.james.mshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.IndexActivity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.webdesign688.mshop.R;

public class CategoryThreeActivity extends Base1Activity {
	private ArrayList<String>  mNameList=new ArrayList<String>();
	private ArrayList<String>  mIDList=new ArrayList<String>();
	
	private ArrayList<String>  mID_Radom_List=new ArrayList<String>();
	private ArrayList<String>  mPicUrl_Radom_List=new ArrayList<String>();
	private ArrayList<String>  mName_Radom_List=new ArrayList<String>();
	
	private ListView mListView;
	private String m_tid;
	private TextView mTv_Radom3;
	private TextView mTv_Radom2;
	private TextView mTv_Radom1;
	
	private TextView  mTv_Title;
	
	private ImageView mIv_Radom3;
	private ImageView mIv_Radom2;
	private ImageView mIv_Radom1;
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private String m_Oid;
	private UILApplication application;
	private String m_Name;
	private String mString_Research;
	public CategoryThreeActivity() {
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clothes);
		mIv_Radom1 = (ImageView) findViewById(R.id.imageView_radom);
		mIv_Radom2 = (ImageView) findViewById(R.id.imageView_radom2);
		mIv_Radom3 = (ImageView) findViewById(R.id.imageView_radom3);
		mTv_Radom1 = (TextView) findViewById(R.id.tv_radom1);
		mTv_Radom2 = (TextView) findViewById(R.id.tv_radom2);
		mTv_Radom3 = (TextView) findViewById(R.id.tv_radom3);
		//mTv_Title= (TextView) findViewById(R.id.tv_three_title);
		
		 application = (UILApplication) getApplication();
		Intent intent = getIntent();
		//獲得id
		m_tid = intent.getStringExtra("tid");
		m_Oid = intent.getStringExtra("oid");
		m_Name = intent.getStringExtra("name");
		Log.e("CategoryThreeActivity_name", m_Name);
		//mTv_Title.setText(m_Name);
		//下載信息
		downLoad();
		//下載隨機的三個圖片和信息
		downLoadRadom();
		//點擊ListView Item

	}
	public  void btn_search(View v) {
        getmEtcontent();
      if ("".equals(mString_Research)||mString_Research==null) {
			showDialog(1);
		}
      else {
			
     	 Intent intent = new Intent(CategoryThreeActivity.this,SearchResultActivity.class);
     	 intent.putExtra("search", mString_Research);
     	 application.setmString_actionbar3("搜索結果");
     	 Log.e("IndexActivitysearch", ""+mString_Research);
     	 startActivity(intent);
		}
}
public void getmEtcontent(){
String string = super.obtainContent();
if ("".equals(string)) {
}
else {
	mString_Research=string;
}
};
private void downLoadRadom() {
		
	new DownRadomAsyTask().execute(Content.RADOM_SECOND+m_tid);
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
               new DownLoadAsyTask().execute(Content.CATEGORY_THREE+m_tid);
	}

class DownLoadAsyTask extends AsyncTask<String, Void, String>{  
	 
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JSONObject jsonObject;
		try {
			
			jsonObject = new JSONObject(result);
			String string_code = jsonObject.getString("code");
			 int  num_code=Integer.valueOf(string_code);
			 if (num_code==1) {
				 JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
				String	string_Id= jsonObject2.getString("thid");
				String	 string_Name = jsonObject2.getString("thname");
				
				
				
				mNameList.add(string_Name);
				mIDList.add(string_Id);
					 Log.e("DownLoadAsyTask", ""+string_Id);
					 Log.e("DownLoadAsyTask", ""+string_Name);
				}
				  findViewById(R.id.progressBar_second).setVisibility(View.GONE);
				  
				 mListView= (ListView) findViewById(R.id.listView_allclothes);
				 mListView.setVisibility(View.VISIBLE);
					selectItem();
				  
				  //適配器初始化
				  initAdapter();
			}
			 else {
				 findViewById(R.id.progressBar_second).setVisibility(View.GONE);
				showDialog(1);
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
class DownRadomAsyTask extends AsyncTask<String, Void, String>{  
	 
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JSONObject jsonObject;
		try {
			
			jsonObject = new JSONObject(result);
			String string_code = jsonObject.getString("code");
			 int  num_code=Integer.valueOf(string_code);
			 if (num_code==1) {
				 JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
				final String	string_Id= jsonObject2.getString("id");
				final String	 string_Name = jsonObject2.getString("name");
				String	 string_PicUrl = jsonObject2.getString("pic");
	                   if (i==0) {
	                	   initImageLoaderOptions();
							imageLoader.displayImage(string_PicUrl,
									mIv_Radom1, options);
							mTv_Radom1.setText(string_Name);
							mIv_Radom1.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(CategoryThreeActivity.this,Tab1Activity.class);
									intent.putExtra("name", string_Name);
									application.setmString_actionbar3(string_Name);
									intent.putExtra("id", string_Id);
									application.setProduct_id(string_Id);
									startActivity(intent);		
									
								}
							});
					}	
	                   if (i==1) {
	                	   initImageLoaderOptions();
							imageLoader.displayImage(string_PicUrl,
									mIv_Radom2, options);
							mTv_Radom2.setText(string_Name);
							mIv_Radom2.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									
									Intent intent = new Intent(CategoryThreeActivity.this,Tab1Activity.class);
									intent.putExtra("name", string_Name);
									application.setmString_actionbar3(string_Name);
									intent.putExtra("id", string_Id);
									application.setProduct_id(string_Id);
									startActivity(intent);				
									
								}
							});
					}		
	                   if (i==2) {
	                	   initImageLoaderOptions();
							imageLoader.displayImage(string_PicUrl,
									mIv_Radom3, options);
							mTv_Radom3.setText(string_Name);
							mIv_Radom3.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									
									Intent intent = new Intent(CategoryThreeActivity.this,Tab1Activity.class);
									intent.putExtra("name", string_Name);
									application.setmString_actionbar3(string_Name);
									intent.putExtra("id", string_Id);
									application.setProduct_id(string_Id);
									startActivity(intent);				
									
								}
							});
					}		
					 Log.e("DownLoadAsyTask", ""+string_Id);
					 Log.e("DownLoadAsyTask", ""+string_Name);
				}
				
				
			}
			  else {
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
           mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent=new Intent(CategoryThreeActivity.this,LatestActivity.class);
				intent.putExtra("thid", mIDList.get(position));
				intent.putExtra("oid", m_Oid);
				intent.putExtra("tid", m_tid);
				intent.putExtra("name",mNameList.get(position));
				application.setmString_actionbar(mNameList.get(position));
				startActivity(intent);
			}
		});
	}
private void initAdapter() {
		mListView.setAdapter(new MyAdapter());
	}
class MyAdapter extends BaseAdapter{
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = getLayoutInflater().inflate(R.layout.item_listview_allclothes, null);
		TextView mTextView= (TextView) layout.findViewById(R.id.tv_man_listview);
		mTextView.setText(mNameList.get(position));
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
		//購物車
		public void btn_shoppingcar(View v) {
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
			if (id==1) {
				return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage("暫無此類商品信息").setPositiveButton(R.string.confirm, null).create();
			}
			else {
				
				return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
			}
			// TODO Auto-generated method stub
		}

}
