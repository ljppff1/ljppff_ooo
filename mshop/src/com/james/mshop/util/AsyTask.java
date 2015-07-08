package com.james.mshop.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;


  class AsyTask extends AsyncTask<String, Void, String>{  
	 
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			String string = jsonObject.getString("code");
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
  class DownRadomAsyTask extends AsyncTask<String, Void, String>{  
		 
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject jsonObject;
			try {
				
				jsonObject = new JSONObject(result);
				JSONArray array = jsonObject.getJSONArray("data");
				  for (int i = 0; i < array.length(); i++) {
					 JSONObject jsonObject2 = array.getJSONObject(i);
				String	string_Id= jsonObject2.getString("id");
				String	 string_Name = jsonObject2.getString("name");
				String	 string_PicUrl = jsonObject2.getString("pic");
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