package com.james.mshop.product;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.webdesign688.mshop.R;
import com.james.mshop.util.Content;
import com.james.mshop.util.getJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeFrame extends Fragment {
    private int position;
    
	private ArrayList<String>  mCategoryIDList=new ArrayList<String>();
	private ArrayList<String>  mCategoryNameList=new ArrayList<String>();
	private ArrayList<String>  mPhoneUrlList=new ArrayList<String>();
    protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageView mImageView;
	private TextView mTextView;

	private String m_Id;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	View layout = inflater.inflate(R.layout.item_welcomebanner, null);
    	mImageView = (ImageView) layout.findViewById(R.id.imageView_welcome_brand);
    //	downLoad();
    	return layout;
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
            new BrandDown().execute(Content.URL_PRODUCT_PIC+m_Id);		
	}
   class BrandDown extends AsyncTask<String, Void, String>{  
		  private String CategoryID;
		private String CategoryName;
		private String PhotoURL;
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				
				jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
							 JSONObject jsonObject2 = array.getJSONObject(i);
						String	string_pid= jsonObject2.getString("pid");
						String	 string_ppic= jsonObject2.getString("ppic");
						 Log.e("ProductPicAsyTask", ""+string_pid);
						 Log.e("ProductPicAsyTask", ""+string_ppic);
					      mPhoneUrlList.add(string_ppic);
						  }
				//	 Log.e("ContactActivity", ""+CategoryID);
				//	 Log.e("ContactActivity", ""+CategoryName);
				//	 Log.e("ContactActivity", ""+PhotoURL);
					//¼ÓÝdˆDÆ¬
					    //UIL
							initImageLoaderOptions();
							imageLoader.displayImage(mPhoneUrlList.get(position%mPhoneUrlList.size()),
									mImageView, options);
							
				    	mImageView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								/*String cId= mCategoryIDList.get(position%mCategoryIDList.size());
								   Intent intent=new Intent(getActivity(),Product1Activity.class);
								   intent.putExtra("cId", cId);
								startActivity(intent);*/
							}
						});
					
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

	public void setPosition(int arg0) {
		this.position=arg0;
		
	}
	public void setPid(String m_Id) {
		this.m_Id=m_Id;
		
	}
}
