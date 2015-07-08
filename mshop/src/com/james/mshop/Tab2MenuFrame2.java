package com.james.mshop;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.james.mshop.MenuFrame1.AddStoreAsyTask;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.webdesign688.mshop.R;





public class Tab2MenuFrame2 extends Fragment 
{  

	private WebView mWebView;
	private TextView mTv_Color1;
	private TextView mTv_Color2;
	private TextView mTv_Color;
	private TextView mTv_Size;
	private ArrayList<String> mColorIdList=new ArrayList<String>();
	private ArrayList<String> mColorNameList=new ArrayList<String>();
	
	private ArrayList<String> mSizeIdList=new ArrayList<String>();
	private ArrayList<String> mSizeNameList=new ArrayList<String>();
	private View rela_Color;
	private boolean hasSize;
	private boolean mIsSize;
	private UILApplication application;
	private String mProduct_Id;
	private MyAdapter mAdapter;
	private GridView mGridView;
	private String mString_Cid;
	private String mString_Sid;
	private boolean mIslogin;
	private TextView textView_addCar;
	private TextView textView_addStore;
	public boolean hasColor;
	private TextView mTv_Share;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		View layout = inflater.inflate(R.layout.activity_pro__detail_tab, null);
		mGridView = (GridView) layout.findViewById(R.id.gridView_color);
		mTv_Color = (TextView) layout.findViewById(R.id.tv_product_color);
		mTv_Size = (TextView) layout.findViewById(R.id.tv_product_size);
		rela_Color = layout.findViewById(R.id.rela_product_color);
		mWebView = (WebView) layout.findViewById(R.id.webView_detail1);
		textView_addCar = (TextView) layout.findViewById(R.id.imageButton_addcar);
		textView_addStore= (TextView) layout.findViewById(R.id.imageButton_addstore);
		mTv_Share = (TextView) layout.findViewById(R.id.tv_product_share);
		application = (UILApplication) getActivity().getApplication();
		mProduct_Id = application.getProduct_id();
		//下載顏色列表
				//downLoadColor();
				
				//下載尺寸列表
				//downLoadSize();
				
				downLoad();
			//	click();
			//	itemSelect();
		return layout; 
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
	}
	private void downLoadSize() {
		new ProductSizeTask().execute(Content.URL_PRODUCTSIZE+mProduct_Id);	
		
	}
	private void downLoadColor() {
		new ProductColorTask().execute(Content.URL_PRODUCTCOLOR+mProduct_Id);
	}
private void click() {
	mTv_Share.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ShareSDK.initSDK(getActivity());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        
        // 分享时Notification的图标和文字
      //  oks.setNotification(R.drawable.icon_share2, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        //oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
       // oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
       // oks.setText("時尚潮流，盡在mshop,       http://josie.i3.com.hk/mshop/mobile/index.php");
        oks.setText("時尚潮流，盡在mshop,       http://josie.i3.com.hk/mshop/mobile/index.php");
        oks.setTitle("mshop");
        oks.setTitleUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
        oks.setSite("mshop");
        oks.setSiteUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
        oks.setImageUrl("http://josie.i3.com.hk/mshop/UPFILE/CategoryPic/201421210303811.jpg");

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
       // oks.setImagePath(R.drawable.ic_launcher);
        // url仅在微信（包括好友和朋友圈）中使用
       // oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
       // oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    //    oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(getActivity());}
	});
	textView_addCar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.e("onclick", "onclick");
			mIslogin = application.getLoginTag();
			if (mIslogin) {
				String m_ID = application.getmMember_Id();
				//http://josie.i3.com.hk/mshop/json/shop_add.php?mid=1&pid=1&cid=1&sid=1&qty=1
				new AddStoreAsyTask().execute(Content.SHOP_ADD+m_ID
						+"&pid="+mProduct_Id
						+"&cid="+mString_Cid
						+"&sid="+mString_Sid
						+"&qty="+"1"
						);
				Toast.makeText(getActivity(), "已加入購物車", Toast.LENGTH_SHORT).show();
			}
			else {
				
				Toast.makeText(getActivity(), "請先登入", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getActivity(),LoginActivity.class));
				application.setmString_actionbar3("登入");
			}
		}
	});
       textView_addStore.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (mIslogin) {
				String m_ID = application.getmMember_Id();
				new AddStoreAsyTask().execute(Content.FAVORITE_ADD+"mid="+m_ID+"&pid="+mProduct_Id);
				Toast.makeText(getActivity(), "已加入收藏", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "請先登入", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(getActivity(),LoginActivity.class));
				application.setmString_actionbar3("登入");
			}
		}
	});	
	mTv_Size.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mIsSize=true;
			if (hasSize) {
				
				mAdapter.notifyDataSetChanged();
			}
			else {
				Builder  dialog=new AlertDialog.Builder(getActivity());
		          dialog.setTitle(R.string.tip).setMessage("暫無相關尺碼信息")
		          .setPositiveButton(R.string.confirm, null);
		          dialog.create().show();
			}
		}
	});
mTv_Color.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			mIsSize=false;
			if (hasColor) {
				
				mAdapter.notifyDataSetChanged();
			}
			else {
				Builder  dialog=new AlertDialog.Builder(getActivity());
		          dialog.setTitle(R.string.tip).setMessage("暫無相關尺碼信息")
		          .setPositiveButton(R.string.confirm, null);
		          dialog.create().show();
			}
		}
	});		
}
private void itemSelect() {

	mGridView.setOnItemClickListener(new OnItemClickListener(
			) {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					
					if (mIsSize) {
						
						mString_Cid = mColorIdList.get(arg2);
						mTv_Size.setText(mSizeNameList.get(arg2));
					}
					else {
						mString_Sid = mColorIdList.get(arg2);
						mTv_Color.setText(mColorNameList.get(arg2));
					}
					
					
				}
	});
	}
	private void downLoad() {
        new ProductAsyTask().execute(Content.PRODUCT_DETAIL+mProduct_Id);		
}
private void initAdapter() {
		
		mAdapter = new MyAdapter();
		mGridView.setAdapter(mAdapter);
		
	}
	class ProductColorTask extends AsyncTask<String, Void, String>{  
		

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
						String	string_cid= jsonObject2.getString("cid");
						String	 string_Cname= jsonObject2.getString("cname");
						mColorIdList.add(string_cid);
						mColorNameList.add(string_Cname);
						//初始化適配器
						initAdapter();
						hasColor=true;
					}
				}
				else {
					hasColor=false;
				}
			} catch (JSONException e) {
				       
				     Builder  dialog=new AlertDialog.Builder(getActivity());
			          dialog.setTitle(R.string.tip).setMessage(R.string.no_internet)
			          .setPositiveButton(R.string.confirm, null);
			          dialog.create().show();
				e.printStackTrace();
			}
		}
			@Override
			protected String doInBackground(String... params) {
				String str=params[0];
				return getJson.getData(str);
			}
			}
	class ProductSizeTask extends AsyncTask<String, Void, String>{  
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
					String	string_cid= jsonObject2.getString("sid");
					String	 string_Cname= jsonObject2.getString("sname");
					mSizeIdList.add(string_cid);
					mSizeNameList.add(string_Cname);
					hasSize=true;
					/*//初始化適配器
					initAdapter();*/
					  }
				}
				else {
					hasSize=false;
				}
				
			} catch (JSONException e) {
				   Builder  dialog=new AlertDialog.Builder(getActivity());
			          dialog.setTitle(R.string.tip).setMessage(R.string.no_internet)
			          .setPositiveButton(R.string.confirm, null);
			          dialog.create().show();
				e.printStackTrace();
			}
		}
			
			
			@Override
			protected String doInBackground(String... params) {
				String str=params[0];
				return getJson.getData(str);
			}
			}
class ProductAsyTask extends AsyncTask<String, Void, String>{  
 
@Override
protected void onPostExecute(String result) {
	super.onPostExecute(result);
	JSONObject jsonObject;
	try {
		
		jsonObject = new JSONObject(result);
		JSONObject data = jsonObject.getJSONObject("data");
		String string_Name = data.getString("name");
		String string_PriceHk1 = data.getString("pricehk1");
		String string_PriceHk2 = data.getString("pricehk2");
		String string_PriceRMB1 = data.getString("pricermb1");
		String string_PriceRMB2 = data.getString("pricermb2");
		String string_Detail1 = data.getString("detail1");
		
		mWebView.getSettings().setUseWideViewPort(true);
		  mWebView.getSettings().setBuiltInZoomControls(true);
		  
		    //轉換
		  String resultss = string_Detail1.replace("\"", "'");
		  String resultss2 = resultss.replace("\\", "");
		  Log.e("resultss", resultss);
		  Log.e("resultss2", resultss2);
	      //顯示數據
		  mWebView.loadDataWithBaseURL(null, resultss2, null, "UTF-8", null);
		
		
			 Log.e("ProductAsyTask", ""+string_Name);
			 Log.e("ProductAsyTask", ""+string_PriceHk1);
			 Log.e("ProductAsyTask", ""+string_PriceHk2);
			 Log.e("ProductAsyTask", ""+string_PriceRMB1);
			 Log.e("ProductAsyTask", ""+string_PriceRMB2);
			 
			 
		  
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
class MyAdapter extends  BaseAdapter{
	@Override   
	public View getView(int position, View convertView, ViewGroup parent) {
		View layout = getActivity().getLayoutInflater().inflate(R.layout.item_grid_product, null);
		TextView mTextView= (TextView) layout.findViewById(R.id.tv_product_gridview);
		if (mIsSize) {
			
			mTextView.setText(mSizeNameList.get(position));
		}
		else {
			
			mTextView.setText(mColorNameList.get(position));
		}
		
		return layout;
	}
	@Override
	public int getCount() {
		if (mIsSize) {
			return mSizeNameList.size();
		}
		else {
			
			return mColorNameList.size() ;
		}
		
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
class AddStoreAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(result);
				String string = jsonObject.getString("code");
				Log.e("AddStoreAsyTask", string);
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
}
