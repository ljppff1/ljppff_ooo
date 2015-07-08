package com.james.mshop;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.webdesign688.mshop.R;

import com.james.mshop.CategoryTwoActivity;
import com.james.mshop.LoginActivity;
import com.james.mshop.RegisterActivity;
import com.james.mshop.owner.CaseManagerActivity;
import com.james.mshop.owner.OwnerActivity;
import com.james.mshop.owner.Tab2Activity;
import com.james.mshop.util.AppManager;
import com.james.mshop.util.Content;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LeftFrament extends Fragment {
    private View inflate;
	private ArrayList<String> mNameList=new ArrayList<String>();
	private ArrayList<String> mICONList=new ArrayList<String>();
	private ArrayList<String> mIDList=new ArrayList<String>();
	private ArrayList<String> mIPICList=new ArrayList<String>();
	
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	
	private ListView mListView;
	private UILApplication application;
	private boolean mLoginTag;
	@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	        inflate = inflater.inflate(R.layout.leftframe, null);  
	return inflate;
}
  @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		application = (UILApplication) getActivity().getApplication();
		mLoginTag = application.getLoginTag();
		
		downLoad();
		
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
	 new DownLoadAsyTask().execute(Content.CATEGORY_ONE);	
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
				String	string_Oid= jsonObject2.getString("oid");
				String	 string_OName = jsonObject2.getString("oname");
				String	 string_Oicon = jsonObject2.getString("oicon");
				String	 string_Opic = jsonObject2.getString("opic");
				mNameList.add(string_OName);
				mICONList.add(string_Oicon);
				mIDList.add(string_Oid);
				mIPICList.add(string_Opic);
					 Log.e("DownLoadAsyTask", ""+string_OName);
					 Log.e("DownLoadAsyTask", ""+string_Oicon);
					 Log.e("DownLoadAsyTask", ""+string_Oid);
					Log.e("DownLoadAsyTask", ""+string_Opic);
				}
				  inflate.findViewById(R.id.progressBar_index).setVisibility(View.GONE);
				  
				 mListView= (ListView) inflate.findViewById(R.id.listView_menu);
				 mListView.setVisibility(View.VISIBLE);
				 initListView();
							//點擊listview
				selectListview();
			} catch (JSONException e) {
			//	getActivity().showDialog(2);
				e.printStackTrace();
			}
		}
			@Override
			protected String doInBackground(String... params) {
				String str=params[0];
				return getJson.getData(str);
			}
			}
private void initListView() {
    MyAdapter adapter=new MyAdapter();
	mListView.setAdapter(adapter);		
}
public class MyAdapter extends BaseAdapter{
@Override
public View getView(int position, View convertView, ViewGroup parent) {
	 View layout = getActivity().getLayoutInflater().inflate(R.layout.item_menu, null);
	 ImageView imageView = (ImageView) layout.findViewById(R.id.imageView_listview_menu);
	 TextView textView= (TextView) layout.findViewById(R.id.textview_listview_textview);
	 if (position==0) {
		   imageView.setBackgroundResource(R.drawable.icon_login);
			 textView.setText("首页");
	}
	 if (position==1) {
		   imageView.setBackgroundResource(R.drawable.icon_login);
			 textView.setText("最新");
	}
	 if (position==2) {
		   imageView.setBackgroundResource(R.drawable.icon_login);
			 textView.setText("特惠");
	}
	 
	 //設置圖片和標題
	 //根據是否為登錄狀態改變字符串
	 if (position>2&&position<(mICONList.size()+3)) {
		
		 initImageLoaderOptions();
		 imageLoader.displayImage(mICONList.get(position-3),
				 imageView, options);
		 textView.setText(mNameList.get(position-3));
	}
 if (position==(mNameList.size()+3)) {
	   imageView.setBackgroundResource(R.drawable.icon_login);
	    if (mLoginTag) {
		 textView.setText("我的賬戶");
	}
	 else {
		 textView.setText("登入");
	}
}
 if (position==mNameList.size()+4) {
	   imageView.setBackgroundResource(R.drawable.icon_key);
	    if (mLoginTag) {
		 textView.setText("我的訂單");
	}
	 else {
		 textView.setText("註冊");
	}
}

	return layout;
}
@Override
public int getCount() {
	// TODO Auto-generated method stub
	return mICONList.size()+5;
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
};

private void selectListview() {
       mListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			int size = mICONList.size()+3;
			if(position==0){
				if(application.getLoginTag()){
					application.setmString_actionbar3("首页");
		        startActivity(new Intent(getActivity(), MainActivity.class));
				}else{
					application.setmString_actionbar3("首页");
			        startActivity(new Intent(getActivity(), WecomeActivity.class));
				}
			AppManager.getAppManager().finishActivity();
			}
			if(position==1){
		        Intent intent=new Intent(getActivity(),IndexNew1Activity.class);
		        application.setmString_actionbar3("最新");
		        intent.putExtra("OT", 1);
				startActivity(intent);
			}
			if(position==2){
		        Intent intent=new Intent(getActivity(),IndexNew1Activity.class);
		        application.setmString_actionbar3("特惠");
		        intent.putExtra("OT", 2);
				startActivity(intent);
			}
			

			if (position>2&&position<(mICONList.size()+3)) {
				//Log.e("Dialog", position+"");
				Intent intent=new Intent(getActivity(),CategoryTwoActivity.class);
				intent.putExtra("oid", mIDList.get(position-3));
				intent.putExtra("name", mNameList.get(position-3));
				application.setmString_actionbar( mNameList.get(position-3));
				startActivity(intent);
			}
			if (position==size) {
				 if (mLoginTag) {
					   Intent intent7=new Intent(getActivity(),OwnerActivity.class);
					   application.setmString_actionbar3("個人中心");
						startActivity(intent7);
				}
				   else {
					
					   Intent intent7=new Intent(getActivity(),LoginActivity.class);
					   application.setmString_actionbar3("登入");
					   startActivity(intent7);
				}
				 
			}
			if (position==size+1) {
				if (mLoginTag) {
					
					   Intent intent8=new Intent(getActivity(),Tab2Activity.class);
					   startActivity(intent8);
					   application.setmString_actionbar3("我的訂單");
				}
				   else {
					   Intent intent8=new Intent(getActivity(),RegisterActivity.class);
					   application.setmString_actionbar3("註冊");
					   startActivity(intent8);
				}
				 
			}
			
		}
	});		
}

}
