package com.james.mshop.owner;

import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.Base3Activity;
import com.james.mshop.IndexActivity;
import com.james.mshop.LoginActivity;
import com.webdesign688.mshop.R;
import com.james.mshop.WecomeActivity;
import com.james.mshop.util.AppManager;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OwnerActivity extends Base3Activity {

	private ListView mListView;
	private String[]   mTitleList=new String[]{"訂單管理","我的收藏","我的地址","使用幫助","關於我們",};
	private String mName;
	private TextView tv_Name;
	private UILApplication application;
	public OwnerActivity(){
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner);
		mListView = (ListView) findViewById(R.id.listView_owner);
		tv_Name = (TextView) findViewById(R.id.tv_owner_name);
		//獲得姓名
		application= (UILApplication) getApplication();
		Intent intent = getIntent();
		mName = intent.getStringExtra("name");
		tv_Name.setText(mName);
		//初始化適配器
		initAdapter();
		//點擊ListView
		selectListview();
	}
	private void selectListview() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				application.setmString_actionbar3(mTitleList[position]);
                   switch (position) {
				case 0:
					startActivity(new Intent(OwnerActivity.this,Tab2Activity.class));
					application.setmString_actionbar3("我的訂單");
					break;
				case 1:
					startActivity(new Intent(OwnerActivity.this,StoreActivity.class));
					break;
				case 2:
					startActivity(new Intent(OwnerActivity.this,MyadressActivity.class));
					break;
				case 3:
					startActivity(new Intent(OwnerActivity.this,HelpActivity.class));
					break;
				case 4:
					startActivity(new Intent(OwnerActivity.this,AboutUsActivity.class));
					break;
				default:
					break;
				}				
			}
		});
	}
	private void initAdapter() {
                mListView.setAdapter(new MyAdapter());		
	}
	public void btn_store(View v) {
            startActivity(new Intent(this,StoreActivity.class));
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			String a ="";
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
class MyAdapter extends BaseAdapter{
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View layout = getLayoutInflater().inflate(R.layout.item_listview_owner, null);
					TextView textView= (TextView) layout.findViewById(R.id.tv_owner_listview);
					textView.setText(mTitleList[position]);
					return layout;
				}
				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return mTitleList.length;
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
			public  void btn_loginout(View v) {
                 UILApplication application = (UILApplication) getApplication();
                 application.setLoginTag(false);
				startActivity(new Intent(this,WecomeActivity.class));
			    AppManager.getAppManager().finishAllActivity();
			}

}
