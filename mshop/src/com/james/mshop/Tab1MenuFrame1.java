package com.james.mshop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.Shake2Share;
import cn.sharesdk.onekeyshare.Shake2Share.OnShakeListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.james.mshop.WecomeActivity.DownLoadAsyTask1;
import com.james.mshop.WecomeActivity.ImageAdapter;
import com.james.mshop.product.MyFrame;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.james.mshop.widgets.MyGallery1;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import com.webdesign688.mshop.R;





public class Tab1MenuFrame1 extends Fragment 
{  
	private UILApplication application;
	private String m_Id;
	private ArrayList<String> mProductPicList=new ArrayList<String>();
	private ArrayList<String> mProductPicIDList=new ArrayList<String>();
	
	private ArrayList<String> mColorIdList=new ArrayList<String>();
	private ArrayList<String> mColorNameList=new ArrayList<String>();
	
	private ArrayList<String> mSizeIdList=new ArrayList<String>();
	private ArrayList<String> mSizeNameList=new ArrayList<String>();
	
	private ImageView mImageView;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private TextView mTv_Color;
	private TextView mTv_Size;
	private View rela_Color;
	private boolean mIsSize;
	
	private boolean hasColor;
	private boolean hasSize;
	private String string_Name;

	private ViewPager viewPager;
	private int i=1;
	private GridView mGridView;
	
	private String mString_Cid;
	private String mString_Sid;
	private MyAdapter mAdapter;
	private boolean mIslogin;
	private View layout;
	private TextView textView_addCar;
	private TextView textView_addStore;
	private TextView mTv_Share;
    private List<String> list =new ArrayList<String>();
    private int preSelImgIndex = 0;
	private LinearLayout ll_focus_indicator_container1;
	private RelativeLayout mRltitle1;
	private FrameLayout mFLtitle1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		layout = inflater.inflate(R.layout.activity_product_tab, null);
		
		//找控件
		findId();
		application = (UILApplication) getActivity().getApplication();
		m_Id = application.getProduct_id();
		downLoad();
		//下載圖片信息
		downloadpic();
		//下載顏色列表
		downLoadColor();
		//下載尺寸列表
		downLoadSize();
		//顏色點擊事件
		itemSelect();
		click();
		return layout; 
	}
	private void downloadpic() {
		new DownLoadAsyTask1().execute("http://josie.i3.com.hk/mshop/json/productpic.php?id="+m_Id);
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
			private void initImageLoaderOptions() {
				options = new DisplayImageOptions.Builder()
						.showStubImage(R.drawable.ic_stub)
						.showImageForEmptyUri(R.drawable.ic_empty)
						.showImageOnFail(R.drawable.ic_error).cacheInMemory()
						.cacheOnDisc().displayer(new FadeInBitmapDisplayer(2000))
						.bitmapConfig(Bitmap.Config.RGB_565).build();
			}
	   private void InitFocusIndicatorContainer() {
			for (int i = 0; i < list.size(); i++) {
			    ImageView localImageView = new ImageView(getActivity());
			    localImageView.setId(i);
			    ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
			    localImageView.setScaleType(localScaleType);
			    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
				    24, 24);
			    localImageView.setLayoutParams(localLayoutParams);
			    localImageView.setPadding(5, 5, 5, 5);
			    localImageView.setImageResource(R.drawable.ic_focus);
			    this.ll_focus_indicator_container1.addView(localImageView);
			}
		    }
	class DownLoadAsyTask1 extends AsyncTask<String, Void, String>{  
		private MyGallery1 gallery;
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

							String CoverPic=jsonObject2.getString("ppic");
                  
						 list.add(CoverPic);	 
					}
						mRltitle1.setVisibility(View.GONE);
						mFLtitle1 .setVisibility(View.VISIBLE);

						ll_focus_indicator_container1 = (LinearLayout) getActivity().findViewById(R.id.ll_focus_indicator_container1);
						InitFocusIndicatorContainer();
						gallery = (com.james.mshop.widgets.MyGallery1)  getActivity().findViewById(R.id.banner_gallery1);
						gallery.setAdapter(new ImageAdapter( getActivity()));
						gallery.setFocusable(true);
						gallery.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
									Intent intent=new Intent(getActivity(),Gallery_Pic_Activity.class);
										intent.putExtra("url", list.get(position));
									startActivity(intent);
							}
						});
						gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> arg0, View arg1,
							    int selIndex, long arg3) {
							//修改上一次选中项的背景
				
						  	selIndex = selIndex % list.size();
						    	
							ImageView preSelImg = (ImageView) ll_focus_indicator_container1.findViewById(preSelImgIndex);
						preSelImg.setImageDrawable( getActivity()
							.getResources().getDrawable(R.drawable.ic_focus));
							//修改当前选中项的背景
							ImageView curSelImg = (ImageView) ll_focus_indicator_container1
								.findViewById(selIndex);
							curSelImg
								.setImageDrawable( getActivity()
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
	

	
	private void shake() {
		final Shake2Share s2s = new Shake2Share();
		// 设置回调，摇晃到一定程度就会触发分享
		s2s.setOnShakeListener(new OnShakeListener() {
		        public void onShake() {
		                OnekeyShare oks = new OnekeyShare();
		                // 设置一个用于截屏分享的View
		                oks.setViewToShare(getView());
		                oks.setText("摇一摇，就分享");
		                oks.setPlatform(WechatMoments.NAME);
		                oks.show(getActivity());
		        }
		});
		s2s.show(getActivity(), null);
	}
	private void findId() {
		mTv_Size = (TextView) layout.findViewById(R.id.tv_product_size);
		mTv_Color= (TextView) layout.findViewById(R.id.tv_product_color);
		mGridView = (GridView)layout.findViewById(R.id.gridView_color);
		textView_addCar = (TextView) layout.findViewById(R.id.imageButton_addcar);
		textView_addStore= (TextView) layout.findViewById(R.id.imageButton_addstore);
		mTv_Share = (TextView) layout.findViewById(R.id.tv_product_share);
		mRltitle1 =(RelativeLayout)layout.findViewById(R.id.mRltitle1);
		mFLtitle1 =(FrameLayout)layout.findViewById(R.id.mFLtitle1);

	}
	protected void dialog(){
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setTitle(R.string.tip);
		builder.setMessage("请先登入")
         .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialog, int id) {  
            	 startActivity(new Intent(getActivity(),LoginActivity.class));
             }  
         })  
         .setNegativeButton("取消", new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialog, int id) {  
             }  
         });  
		builder.create();
				
	}
	private void click() {
		//修改的这个分享。
		mTv_Share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  ShareSDK.initSDK(getActivity());
			        OnekeyShare oks = new OnekeyShare();
			        //关闭sso授权
			        oks.disableSSOWhenAuthorize();
			        
			        // 分享时Notification的图标和文字
			        //oks.setNotification(R.drawable.icon_share2, getString(R.string.app_name));
			        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
			        //oks.setTitle(getString(R.string.share));
			        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
			       // oks.setTitleUrl("http://sharesdk.cn");
			        // text是分享文本，所有平台都需要这个字段
			     //   oks.setText("時尚潮流，盡在mshop,       http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setText("時尚潮流，盡在mshop,       http://josie.i3.com.hk/mshop/mobile/index.php");
			        if(!TextUtils.isEmpty(string_Name)){
			        	 oks.setTitle(string_Name);
			        }else{
			        	 oks.setTitle("mshop");
			        }
			        
			        oks.setTitleUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setSite("mshop");
			        oks.setSiteUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
			        if(list!=null&&list.size()>0){
				        oks.setImageUrl(list.get(0));
			        }else{
			        oks.setImageUrl("http://josie.i3.com.hk/mshop/UPFILE/CategoryPic/201421210303811.jpg");
			        }
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
			        oks.show(getActivity());
			
			}
		});
		textView_addCar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.e("onclick", "onclick");
				mIslogin = application.getLoginTag();
				if (mIslogin) {
					if ("".equals(mString_Cid)||mString_Cid==null) {
						Builder  dialog=new AlertDialog.Builder(getActivity());
				          dialog.setTitle(R.string.tip).setMessage("請選擇顏色")
				          .setPositiveButton(R.string.confirm, null);
				          dialog.create().show();
					}
					else {
						if ("".equals(mString_Sid)||mString_Sid==null) {
							Builder  dialog=new AlertDialog.Builder(getActivity());
					          dialog.setTitle(R.string.tip).setMessage("請選擇尺碼")
					          .setPositiveButton(R.string.confirm, null);
					          dialog.create().show();
						}
						else {
							String m_ID = application.getmMember_Id();
							//http://josie.i3.com.hk/mshop/json/shop_add.php?mid=1&pid=1&cid=1&sid=1&qty=1
							new AddStoreAsyTask().execute(Content.SHOP_ADD+m_ID
									+"&pid="+m_Id
									+"&cid="+mString_Cid
									+"&sid="+mString_Sid
									+"&qty="+"1"
									);
						}
					}
					
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
						mIslogin = application.getLoginTag();
				if (mIslogin) {
					String m_ID = application.getmMember_Id();
					new AddStoreAsyTask1().execute(Content.FAVORITE_ADD+"mid="+m_ID+"&pid="+m_Id);
				//	Toast.makeText(getActivity(), "已加入收藏", Toast.LENGTH_SHORT).show();
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
							
							mString_Sid = mSizeIdList.get(arg2);
							mTv_Size.setText(mSizeNameList.get(arg2));
						}
						else {
							mString_Cid = mColorIdList.get(arg2);
							mTv_Color.setText(mColorNameList.get(arg2));
						}
						
						
					}
		});		
	}
	private void downLoadSize() {
		new ProductSizeTask().execute(Content.URL_PRODUCTSIZE+m_Id);		
	}
	private void downLoadColor() {
		new ProductColorTask().execute(Content.URL_PRODUCTCOLOR+m_Id);		
	}
	private void downLoad() {
		new ProductAsyTask().execute(Content.PRODUCT_DETAIL+m_Id);
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
				 string_Name = data.getString("name");
				String string_PriceHk1 = data.getString("pricehk1");
				String string_PriceHk2 = data.getString("pricehk2");
				String string_PriceRMB1 = data.getString("pricermb1");
				String string_PriceRMB2 = data.getString("pricermb2");
				
				
					 Log.e("ProductAsyTask", ""+string_Name);
					 Log.e("ProductAsyTask", ""+string_PriceHk1);
					 Log.e("ProductAsyTask", ""+string_PriceHk2);
					 Log.e("ProductAsyTask", ""+string_PriceRMB1);
					 Log.e("ProductAsyTask", ""+string_PriceRMB2);
					 
					 TextView tv_Name= (TextView) layout.findViewById(R.id.tv_pro_name);
					 TextView tv_Pri_Hk= (TextView) layout.findViewById(R.id.tv_pro_hk);
					 TextView tv_Pri_RMB= (TextView) layout.findViewById(R.id.tv_pro_rmb);
					 
					 tv_Name.setText(string_Name);
					 tv_Pri_Hk.setText("HK$"+string_PriceHk1);
					 tv_Pri_RMB.setText("￥"+string_PriceRMB1);
				  
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
	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
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
private void initAdapter() {
		
		mAdapter = new MyAdapter();
		mGridView.setAdapter(mAdapter);
		
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
			String code = jsonObject.getString("code");
			if ("1".equals(code)) {
				Toast.makeText(getActivity(), "已加入購物車", Toast.LENGTH_SHORT).show();
				int num_Shopcar = application.getNum_Shopcar();
				application.setNum_Shopcar(num_Shopcar+1);
			}
			else {
				Builder  dialog=new AlertDialog.Builder(getActivity()).setTitle(R.string.tip).setMessage("未加入購物車").setPositiveButton(R.string.confirm, null);
				dialog.create();
				dialog.show(); 
			}
			Log.e("AddStoreAsyTask", code);
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

class AddStoreAsyTask1 extends AsyncTask<String, Void, String>{  
	 
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String code = jsonObject.getString("code");
			String msg1 =  jsonObject.getString("msg");
			if ("1".equals(code)) {
				Toast.makeText(getActivity(), "加入收藏夾成功", Toast.LENGTH_SHORT).show();
			}
			else {
				if(msg1.equals("Exsits")){
					Toast.makeText(getActivity(), "已加入收藏夾", Toast.LENGTH_SHORT).show();

				}else{
				Builder  dialog=new AlertDialog.Builder(getActivity()).setTitle(R.string.tip)
						.setMessage("加入收藏夾失敗").setPositiveButton(R.string.confirm, null);
				dialog.create();
				dialog.show(); 
				}
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

//@Override
/*public void onClick(View v) {
	 switch (v.getId()) {
		case R.id.imageButton_addcar:
			Toast.makeText(getActivity(), "已加入購物車", Toast.LENGTH_SHORT).show();
			Log.e("onclick", "onclick");
			mIslogin = application.getLoginTag();
			if (mIslogin) {
				String m_ID = application.getmMember_Id();
				//http://josie.i3.com.hk/mshop/json/shop_add.php?mid=1&pid=1&cid=1&sid=1&qty=1
				new AddStoreAsyTask().execute(Content.SHOP_ADD+m_ID
						+"&pid="+m_Id
						+"&cid="+mString_Cid
						+"&sid="+mString_Sid
						+"&qty="+"1"
						);
				Toast.makeText(getActivity(), "已加入購物車", Toast.LENGTH_SHORT).show();
			}
			else {
				dialog();
			}
			break;
   case R.id.imageButton_addstore:
	   Toast.makeText(getActivity(), "已加入收藏", Toast.LENGTH_SHORT).show();
			break;


		default:
			break;
		}
	
}*/
}
