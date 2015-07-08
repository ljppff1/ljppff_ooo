package com.james.mshop;

import java.io.IOException;
import java.net.URL;

import com.james.mshop.util.AppManager;



import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.webdesign688.mshop.R;

public class Gallery_Pic_Activity extends Activity {

	private ImageView mImageView;
	private String mString_url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery__pic_);
		AppManager.getAppManager().addActivity(Gallery_Pic_Activity.this);
		mImageView = (ImageView) findViewById(R.id.iv_photo);
		TextView tv_picname= (TextView) findViewById(R.id.tv_current_matrix);
		
		Intent intent = getIntent();
		mString_url = intent.getStringExtra("url");
			tv_picname.setText("(雙擊放大圖片)");
	        
		
	       new DownloadImageTask().execute(mString_url) ;  
	       
	        
	      /*  mCurrMatrixTv = (TextView) findViewById(R.id.tv_current_matrix);

	        Drawable bitmap = getResources().getDrawable(R.drawable.wallpaper);
	        mImageView.setImageDrawable(bitmap);

	        // The MAGIC happens here!
	        mAttacher = new PhotoViewAttacher(mImageView);

	        // Lets attach some listeners, not required though!
	        mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
	        mAttacher.setOnPhotoTapListener(new PhotoTapListener());*/
	    }
	    @SuppressLint("NewApi")
		class DownloadImageTask extends AsyncTask<String, Void, Drawable>   
	    {  
	              
	            protected Drawable doInBackground(String... urls) {  
	                return loadImageFromNetwork(urls[0]);  
	            }  
	      
	            protected void onPostExecute(Drawable result) {  
	                mImageView.setImageDrawable(result);  
	            }  
	    }  
		private Drawable loadImageFromNetwork(String imageUrl)  
		{  
		    Drawable drawable = null;  
		    try {  
		        // 可以在这里通过文件名来判断，是否本地有此图片  
		        drawable = Drawable.createFromStream(  
		                new URL(imageUrl).openStream(), "image.jpg");  
		    } catch (IOException e) {  
		      //  Log.d("test", e.getMessage());  
		    }  
		    if (drawable == null) {  
		        Log.d("test", "null drawable");  
		    } else {  
		        Log.d("test", "not null drawable");  
		    }  
		      
		    return drawable ;  
		}  

}
