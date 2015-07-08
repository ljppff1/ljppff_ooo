package com.james.mshop;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.webdesign688.mshop.R;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.Shake2Share;
import cn.sharesdk.onekeyshare.Shake2Share.OnShakeListener;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.james.mshop.product.MyFrame;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class MenuFrame1 extends Fragment 
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		layout = inflater.inflate(R.layout.activity_product, null);
		
		//找控件
		findId();
		application = (UILApplication) getActivity().getApplication();
		m_Id = application.getProduct_id();
		downLoad();
		//下載圖片信息
		//下載顏色列表
		downLoadColor();
		//下載尺寸列表
		downLoadSize();
		//顏色點擊事件
		initViewPage();
		itemSelect();
		click();
		return layout; 
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
			   /*     oks.setText("時尚潮流，盡在mshop,       http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setTitle("mshop");
			        oks.setTitleUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setSite("mshop");
			        oks.setSiteUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setImageUrl("http://josie.i3.com.hk/mshop/UPFILE/CategoryPic/201421210303811.jpg");
*/
			        oks.setText("分享的文本");
			        oks.setTitle("标题");
			        oks.setTitleUrl("http://user.qzone.qq.com/441819523/infocenter?ptsig=0jXWL3Z3jJwHdwYiXNucF9Wcji5tRhyfzPPIbcMTneU_");
			        oks.setSite("测试干什么的");
			        oks.setSiteUrl("http://sharesdk.cn");
			        oks.setImageUrl("http://wy.eqbang.com/other/image/logo72.png");
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
			        //oks.setComment("我是测试评论文本");
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
	private void initViewPage() {

		viewPager = (ViewPager) layout.findViewById(R.id.pager);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
            viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 10000;
			}
			
			@Override
			public Fragment getItem(int arg0) {
		
			MyFrame myFrame = new MyFrame();
				myFrame.setPosition(arg0);
				myFrame.setPid(m_Id);
				return myFrame ;
			}
		});
		viewPager.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				viewPager.setCurrentItem(5000+i);
				i++;
				viewPager.postDelayed(this, 4000);
			}
		}, 1000);
	
		
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
				String string_Name = data.getString("name");
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
