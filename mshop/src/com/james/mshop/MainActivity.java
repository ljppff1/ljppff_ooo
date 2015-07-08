package com.james.mshop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.product.WelcomeFrame;
import com.james.mshop.util.AppManager;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.james.mshop.widgets.MyGallery;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.webdesign688.mshop.R;

public class MainActivity extends Base3Activity {
	public MainActivity() {
		super(R.string.hello_world);
		// TODO Auto-generated constructor stub
	}
	private int i=1;
	private ViewPager viewPager;  
	public GridView mGridview;
	private ArrayList<Data>  mDataList=new ArrayList<MainActivity.Data>();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private UILApplication application;
	private boolean hasPressedBack;
    private List<String> list =new ArrayList<String>();
    private int preSelImgIndex = 0;
	private LinearLayout ll_focus_indicator_container;
	private RelativeLayout mRltitle;
	private FrameLayout mFLtitle;

	private Handler mHandler=new Handler();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wecome);
		application = (UILApplication) getApplication();
		application.setmString_actionbar3("首页");

		mRltitle =(RelativeLayout)findViewById(R.id.mRltitle);
		mFLtitle =(FrameLayout)findViewById(R.id.mFLtitle);
		downloadnavigation();
		//download information 
		downLoad();
		//initViewpage
	//	initViewPage();
		
	}
	 public class ImageAdapter extends BaseAdapter {
			private Context _context;

			public ImageAdapter(Context context) {
			    _context = context;
			}

			public int getCount() {
			    return list.size();
			}

			public Object getItem(int position) {

			    return position;
			}

			public long getItemId(int position) {
			    return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewHolder = null;
				if(convertView == null)
				{
					viewHolder = new ViewHolder();
					ImageView imageView = new ImageView(_context);
				    imageView.setAdjustViewBounds(true);
				    imageView.setScaleType(ScaleType.FIT_XY);
				    imageView.setLayoutParams(new Gallery.LayoutParams(
					    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				    convertView = imageView;
				    viewHolder.imageView = (ImageView)convertView; 
				    convertView.setTag(viewHolder);
					
				}
				else
				{
					viewHolder = (ViewHolder)convertView.getTag();
				}
			  //  viewHolder.imageView.setImageDrawable(imgList.get(position%imgList.size()));
		        initImageLoaderOptions();
					imageLoader.displayImage(list.get(position),
							viewHolder.imageView, options);

			    return convertView;
			}
			
		    }
		    
		    private static class ViewHolder
			{
				ImageView imageView;
			}

	   private void InitFocusIndicatorContainer() {
			for (int i = 0; i < list.size(); i++) {
			    ImageView localImageView = new ImageView(this);
			    localImageView.setId(i);
			    ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
			    localImageView.setScaleType(localScaleType);
			    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
				    24, 24);
			    localImageView.setLayoutParams(localLayoutParams);
			    localImageView.setPadding(5, 5, 5, 5);
			    localImageView.setImageResource(R.drawable.ic_focus);
			    this.ll_focus_indicator_container.addView(localImageView);
			}
		    }

	private void downloadnavigation() {
		new DownLoadAsyTask1().execute("http://josie.i3.com.hk/mshop/json/bannerlist.php");
	}
	class DownLoadAsyTask1 extends AsyncTask<String, Void, String>{  
		private MyGallery gallery;
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

							String CoverPic=jsonObject2.getString("photo");
                  
						 list.add(CoverPic);	 
					}
						mRltitle.setVisibility(View.GONE);
						mFLtitle .setVisibility(View.VISIBLE);

						ll_focus_indicator_container = (LinearLayout) findViewById(R.id.ll_focus_indicator_container);
						InitFocusIndicatorContainer();
						gallery = (com.james.mshop.widgets.MyGallery) findViewById(R.id.banner_gallery);
						gallery.setAdapter(new ImageAdapter(getApplicationContext()));
						gallery.setFocusable(true);
						gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

						    public void onItemSelected(AdapterView<?> arg0, View arg1,
							    int selIndex, long arg3) {
							//修改上一次选中项的背景
						    	selIndex = selIndex % list.size();
						    	
							ImageView preSelImg = (ImageView) ll_focus_indicator_container.findViewById(preSelImgIndex);
						preSelImg.setImageDrawable(MainActivity.this
							.getResources().getDrawable(R.drawable.ic_focus));
							//修改当前选中项的背景
							ImageView curSelImg = (ImageView) ll_focus_indicator_container
								.findViewById(selIndex);
							curSelImg
								.setImageDrawable(MainActivity.this
									.getResources().getDrawable(
										R.drawable.ic_focus_select));
							preSelImgIndex = selIndex;
						    }

						    public void onNothingSelected(AdapterView<?> arg0) {
						    }
						});

				}
				 else {}
			} catch (JSONException e) {
			}
				
		}
			@Override
			protected String doInBackground(String... params) {
				String str=params[0];
				return getJson.getData(str);
			}
			}
	
	
	@Override
	protected void onResume() {
		application.setmString_actionbar3("首页");

		super.onResume();
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
					  Data data=new Data();
					 JSONObject jsonObject2 = array.getJSONObject(i);
			     	 data.string_Oid= jsonObject2.getString("oid");
			         data.string_OName = jsonObject2.getString("oname");
			         data.string_Opic = jsonObject2.getString("opic");
			         Log.e(" data.string_Opic",  data.string_Opic);
			         mDataList.add(data);
				}
				  Log.e("DownLoadAsyTask", ""+mDataList.toString());
				 //初始化listView
				  mGridview= (GridView) findViewById(R.id.gridView_welcome);
				  initGridView();
			//點擊listview
				selectGridview();
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
	 class Data{
		 String  string_OName;
		 String  string_Oid;
		 @Override
		public String toString() {
			return "Data [string_OName=" + string_OName + ", string_Oid="
					+ string_Oid + ", string_Opic=" + string_Opic + "]";
		}
		String  string_Opic;
	 }
	public void selectGridview() {
		mGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Log.e("Dialog", position+"");
				Intent intent=new Intent(MainActivity.this,CategoryTwoActivity.class);
				intent.putExtra("oid", mDataList.get(position).string_Oid);
				intent.putExtra("name",mDataList.get(position).string_OName);
				application.setmString_actionbar( mDataList.get(position).string_OName);
				startActivity(intent);
			}
		});
	}
	public void initGridView() {
 		mGridview.setAdapter(new MyAdapter());
	}
	class MyAdapter extends BaseAdapter{
    	@Override
		public View getView(int position, View convertView, ViewGroup parent) {  
    		
    		View layout = getLayoutInflater().inflate(R.layout.item_welcome_gridview, null);
    		ImageView mImageView= (ImageView) layout.findViewById(R.id.iv_welcome_pic);
    		TextView tv_Name= (TextView) layout.findViewById(R.id.iv_welcome_name);
    		Log.e("tv_Name", mDataList.toString());
    		Log.e("position", "position"+position);
    		Log.e("tv_Name", mDataList.get(position).string_OName);
    		
    		Log.e("mImageView_URL", mDataList.get(position).string_Opic);
    	         initImageLoaderOptions();
				imageLoader.displayImage("http://josie.i3.com.hk//mshop//UPFILE//CategoryPic//201421211094438.jpg",
						mImageView, options);
    		tv_Name.setText(mDataList.get(position).string_OName);
    		
			return layout;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataList.size();
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
	public  void btn_sales(View v) {    
        Intent intent=new Intent(getApplicationContext(),IndexNew1Activity.class);
        application.setmString_actionbar3("特惠");
        intent.putExtra("OT", 2);
		startActivity(intent);
	}
	public  void btn_latest(View v) {
        Intent intent=new Intent(getApplicationContext(),IndexNew1Activity.class);
        application.setmString_actionbar3("最新");
        intent.putExtra("OT", 1);
		startActivity(intent);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:  
			if (!hasPressedBack)
            {
                // 第一次按
                hasPressedBack = true;
                Toast.makeText(MainActivity.this, "再按一次back鍵退出", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {   
                        hasPressedBack = false;
                    }
                }, 3000);  
                return true;
            }else{
            	AppManager.getAppManager().AppExit(getApplicationContext());
            }
            
			break;

		default:
			break;
		}
    	return super.onKeyDown(keyCode, event);
    }
}
