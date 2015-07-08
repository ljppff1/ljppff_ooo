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
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.webdesign688.mshop.R;

public class CategoryTwoActivity extends Base1Activity {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ListView mListView;
	private ArrayList<String>  mNameList=new ArrayList<String>();
	private ArrayList<String>  mIDList=new ArrayList<String>();
	private ArrayList<String>  mPicUrlList=new ArrayList<String>();
	private String mId_one;
	private String mName;
	private EditText mEditText;
	private String mString_Research;
	public CategoryTwoActivity() {
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categorytwo);
		//TextView tv_Name = (TextView) findViewById(R.id.tv_categorytwo_name);
		//獲得一級信息
		Intent intent = getIntent();
		mId_one = intent.getStringExtra("oid");
		mName = intent.getStringExtra("name");
		Log.e("CategoryTwoActivity_name", mName);
	//	tv_Name.setText(mName);
		//下載信息
		downLoad();
		//點擊ListView
	}
	public  void btn_search(View v) {
        getmEtcontent();
      if ("".equals(mString_Research)||mString_Research==null) {
			showDialog(1);
		}
      else {
			
     	 Intent intent = new Intent(CategoryTwoActivity.this,SearchResultActivity.class);
     	 intent.putExtra("search", mString_Research);
     	 UILApplication application = (UILApplication) getApplication();
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
	private void selectItem() {
mListView.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		Intent intent=new Intent(CategoryTwoActivity.this,CategoryThreeActivity.class);
		intent.putExtra("tid", mIDList.get(position));
		intent.putExtra("oid", mId_one);
		intent.putExtra("name", mNameList.get(position));
		UILApplication application= (UILApplication) getApplication();
		application.setmString_actionbar(mNameList.get(position));
		startActivity(intent);
		
	}
})	;	
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
         		new DownLoadAsyTask().execute(Content.CATEGORY_TWO+mId_one);
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
					String	string_Id= jsonObject2.getString("tid");
					String	 string_Name = jsonObject2.getString("tname");
					String	 string_pic_URL = jsonObject2.getString("tpic");
					mNameList.add(string_Name);
					mPicUrlList.add(string_pic_URL);
					mIDList.add(string_Id);
						 Log.e("DownLoadAsyTask", ""+string_Id);
						 Log.e("DownLoadAsyTask", ""+string_Name);
						 Log.e("DownLoadAsyTask", ""+string_pic_URL);
					}
					  findViewById(R.id.progressBar_man).setVisibility(View.GONE);
					  
					 mListView= (ListView) findViewById(R.id.listView_man_1);
					 mListView.setVisibility(View.VISIBLE);
					  
					 selectItem();
					  //適配器初始化
					  initAdapter();
				}
				 else {
					 findViewById(R.id.progressBar_man).setVisibility(View.GONE);
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
	private void initAdapter() {
		
		mListView.setAdapter(new Myadapter());
		
	}
	class Myadapter extends BaseAdapter{
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			  View layout = getLayoutInflater().inflate(R.layout.item_listview_man, null);
			           ImageView imageView= (ImageView) layout.findViewById(R.id.iv_listview_man);
			           TextView textView= (TextView)layout. findViewById(R.id.tv_listview_man);
			           initImageLoaderOptions();
						imageLoader.displayImage(mPicUrlList.get(position),
								imageView, options);
						textView.setText(mNameList.get(position));
			return layout;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPicUrlList.size();
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
