package com.james.mshop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.james.mshop.product.LatestActivity;
import com.james.mshop.util.AppManager;
import com.james.mshop.util.Content;
import com.james.mshop.util.UILApplication;
import com.james.mshop.util.getJson;
import com.paypal.android.sdk.m;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.webdesign688.mshop.R;

public class BrushActivity extends Base3Activity {
	private ArrayList<String> mNameList=new ArrayList<String>();
	private ArrayList<String> mIDList=new ArrayList<String>();
	private ArrayList<Boolean>  mCheckList=new ArrayList<Boolean>();
	private ListView mListView;
	private MyAdapter mAdapter;
	private String ot;
	public BrushActivity() {
		super(R.string.hello_world);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brush);
		
		mListView = (ListView) findViewById(R.id.listView_brush);
		ot=getIntent().getStringExtra("ST");
		TextView tv_Confirm= (TextView) findViewById(R.id.tv_brush_confirm);
		TextView tv_Cancel= (TextView) findViewById(R.id.tv_brush_cancel);
		tv_Confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				StringBuilder stringBuilder=new StringBuilder();
				Log.e("tv_Confirm", mCheckList.toString());
				
				 for (int i = 0; i < mCheckList.size(); i++) {
                	 if (mCheckList.get(i)) {
						stringBuilder.append("|");
						stringBuilder.append(mIDList.get(i));
					}
				}	
				 stringBuilder.append("|");
				 String string = stringBuilder.toString();
				 Log.e("stringBuilder", string);
				 Intent intent=new Intent();
				 intent.putExtra("keyword", string);
				 UILApplication application= (UILApplication) getApplication();
                if(!TextUtils.isEmpty(ot)){
				 if(ot.equals("1")){
					 application.setmString_actionbar3("最新");
				 }else if(ot.equals("2")){
					 application.setmString_actionbar3("特惠");
				 }
                }
					setResult(2, intent);
				AppManager.getAppManager().finishActivity();
				
/*				 Intent intent=new Intent(BrushActivity.this,BrushResultActivity.class);
				 UILApplication application= (UILApplication) getApplication();
				 application.setmString_actionbar3("最新");
				 intent.putExtra("keyword", string);
				startActivity(intent);
				finish();
*/	
				}
		  });
		tv_Cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
 				
                 for (int i = 0; i < mCheckList.size(); i++) {
                	 mCheckList.set(i, false);
				}				
                 mAdapter.notifyDataSetChanged();
			}
		  });
		//下載一級分類Id
		downLoad();
	}
   private void downLoad() {
	   new DownLoadAsyTask().execute(Content.CATEGORY_ONE);
	}
   
   private void initListView() {
   	mAdapter = new MyAdapter();
		mListView.setAdapter(mAdapter);
   }
   
private void selectListview() {
	mListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			mCheckList.set(arg2, true);
			mAdapter.notifyDataSetChanged();
			
		}
	});
}
class MyAdapter extends BaseAdapter{
	   @Override
		public View getView(int position, View convertView, ViewGroup parent) {
		   View layout = getLayoutInflater().inflate(R.layout.item_listview_brush, null);
		   TextView   tv_Name = (TextView) layout.findViewById(R.id.tv_listview_brush_name);
		   CheckBox   checkBox = (CheckBox) layout.findViewById(R.id.checkBox1);
		   if (mCheckList.get(position)!=true) {
			   checkBox.setChecked(false);
		   }
		   else {
			   checkBox.setChecked(true);
		}
		   
		   tv_Name.setText(mNameList.get(position));
		   
			return layout;
		}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mNameList.size();
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
				
			      	boolean  a=false;
				 mNameList.add(string_OName);
				 mIDList.add(string_Oid);
				 mCheckList.add(a);
					 Log.e("DownLoadAsyTask", ""+string_OName);
					 Log.e("DownLoadAsyTask", ""+string_Oid);
				}
				  
				  
				//點擊listview
				selectListview();
				 //適配器初始化
				initListView();
					findViewById(R.id.mPBbrush).setVisibility(View.GONE);
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


   @Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return new AlertDialog.Builder(this).setTitle(R.string.tip).setMessage(R.string.no_internet).setPositiveButton(R.string.confirm, null).create();
	}

}
