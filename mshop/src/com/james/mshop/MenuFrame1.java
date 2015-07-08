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
		
		//�ҿؼ�
		findId();
		application = (UILApplication) getActivity().getApplication();
		m_Id = application.getProduct_id();
		downLoad();
		//���d�DƬ��Ϣ
		//���d�ɫ�б�
		downLoadColor();
		//���d�ߴ��б�
		downLoadSize();
		//�ɫ�c���¼�
		initViewPage();
		itemSelect();
		click();
		return layout; 
	}
	private void shake() {
		final Shake2Share s2s = new Shake2Share();
		// ���ûص���ҡ�ε�һ���̶Ⱦͻᴥ������
		s2s.setOnShakeListener(new OnShakeListener() {
		        public void onShake() {
		                OnekeyShare oks = new OnekeyShare();
		                // ����һ�����ڽ��������View
		                oks.setViewToShare(getView());
		                oks.setText("ҡһҡ���ͷ���");
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
		builder.setMessage("���ȵ���")
         .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
             public void onClick(DialogInterface dialog, int id) {  
            	 startActivity(new Intent(getActivity(),LoginActivity.class));
             }  
         })  
         .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {  
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
			        //�ر�sso��Ȩ
			        oks.disableSSOWhenAuthorize();
			        
			        // ����ʱNotification��ͼ�������
			        //oks.setNotification(R.drawable.icon_share2, getString(R.string.app_name));
			        // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
			        //oks.setTitle(getString(R.string.share));
			        // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
			       // oks.setTitleUrl("http://sharesdk.cn");
			        // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
			   /*     oks.setText("�r�г������M��mshop,       http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setTitle("mshop");
			        oks.setTitleUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setSite("mshop");
			        oks.setSiteUrl("http://josie.i3.com.hk/mshop/mobile/index.php");
			        oks.setImageUrl("http://josie.i3.com.hk/mshop/UPFILE/CategoryPic/201421210303811.jpg");
*/
			        oks.setText("������ı�");
			        oks.setTitle("����");
			        oks.setTitleUrl("http://user.qzone.qq.com/441819523/infocenter?ptsig=0jXWL3Z3jJwHdwYiXNucF9Wcji5tRhyfzPPIbcMTneU_");
			        oks.setSite("���Ը�ʲô��");
			        oks.setSiteUrl("http://sharesdk.cn");
			        oks.setImageUrl("http://wy.eqbang.com/other/image/logo72.png");
			        // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
			       // oks.setImagePath(R.drawable.ic_launcher);
			        // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
			       // oks.setUrl("http://sharesdk.cn");
			        // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
			        //oks.setComment("���ǲ��������ı�");
			        // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
			       // oks.setSite(getString(R.string.app_name));
			        // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
			    //    oks.setSiteUrl("http://sharesdk.cn");
			        //oks.setComment("���ǲ��������ı�");
			        // ��������GUI
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
				          dialog.setTitle(R.string.tip).setMessage("Ո�x���ɫ")
				          .setPositiveButton(R.string.confirm, null);
				          dialog.create().show();
					}
					else {
						if ("".equals(mString_Sid)||mString_Sid==null) {
							Builder  dialog=new AlertDialog.Builder(getActivity());
					          dialog.setTitle(R.string.tip).setMessage("Ո�x��ߴa")
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
					
					Toast.makeText(getActivity(), "Ո�ȵ���", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(),LoginActivity.class));
					application.setmString_actionbar3("����");
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
				//	Toast.makeText(getActivity(), "�Ѽ����ղ�", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(getActivity(), "Ո�ȵ���", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getActivity(),LoginActivity.class));
					application.setmString_actionbar3("����");
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
				          dialog.setTitle(R.string.tip).setMessage("���o���P�ߴa��Ϣ")
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
				          dialog.setTitle(R.string.tip).setMessage("���o���P�ߴa��Ϣ")
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
					/*//��ʼ���m����
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
					 tv_Pri_RMB.setText("��"+string_PriceRMB1);
				  
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
						//��ʼ���m����
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
				Toast.makeText(getActivity(), "�Ѽ���ُ��܇", Toast.LENGTH_SHORT).show();
				int num_Shopcar = application.getNum_Shopcar();
				application.setNum_Shopcar(num_Shopcar+1);
			}
			else {
				Builder  dialog=new AlertDialog.Builder(getActivity()).setTitle(R.string.tip).setMessage("δ����ُ��܇").setPositiveButton(R.string.confirm, null);
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
				Toast.makeText(getActivity(), "�����ղ؊A�ɹ�", Toast.LENGTH_SHORT).show();
			}
			else {
				if(msg1.equals("Exsits")){
					Toast.makeText(getActivity(), "�Ѽ����ղ؊A", Toast.LENGTH_SHORT).show();

				}else{
				Builder  dialog=new AlertDialog.Builder(getActivity()).setTitle(R.string.tip)
						.setMessage("�����ղ؊Aʧ��").setPositiveButton(R.string.confirm, null);
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
			Toast.makeText(getActivity(), "�Ѽ���ُ��܇", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(getActivity(), "�Ѽ���ُ��܇", Toast.LENGTH_SHORT).show();
			}
			else {
				dialog();
			}
			break;
   case R.id.imageButton_addstore:
	   Toast.makeText(getActivity(), "�Ѽ����ղ�", Toast.LENGTH_SHORT).show();
			break;


		default:
			break;
		}
	
}*/
}
