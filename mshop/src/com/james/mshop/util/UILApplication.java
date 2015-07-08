/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.james.mshop.util;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class UILApplication extends Application {
	private ArrayList<Activity> activityList;
	private boolean isFisrt=true;
	private int curren_Position;
	private boolean isLogined;
	private String Product_Id;
	private String mMember_Id;
	private String mString_actionbar;
	private String mString_actionbar3;
	private int       num_Shopcar;
	public int getNum_Shopcar() {
		return num_Shopcar;
	}
	public void setNum_Shopcar(int num_Shopcar) {
		this.num_Shopcar = num_Shopcar;
	}
	public String getmString_actionbar3() {
		return mString_actionbar3;
	}
	public void setmString_actionbar3(String mString_actionbar3) {
		this.mString_actionbar3 = mString_actionbar3;
	}
	public String getmString_actionbar() {
		return mString_actionbar;
	}
	public void setmString_actionbar(String mString_actionbar) {
		this.mString_actionbar = mString_actionbar;
	}
	public String getmMember_Id() {
		return mMember_Id;
	}
	public void setmMember_Id(String mMember_Id) {
		this.mMember_Id = mMember_Id;
	}
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		super.onCreate();
        activityList = new ArrayList<Activity>();
		initImageLoader(getApplicationContext());
	}
	public void setLoginTag(boolean isLogined) {
        this.isLogined=isLogined;	
}
public boolean getLoginTag(){
return isLogined;
};
	public void addAct(Activity activity) {
		activityList.add(activity);
          }
    public void removeAct(Activity activity) {
    	activityList.remove(activity);
     
	}
    public void exit() {
		for (int i = 0; i < activityList.size(); i++) {
			activityList.get(i).finish();
		}

	}
   
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them, 
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.enableLogging() // Not necessary in common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	public void setIsFirtst(boolean n) {
           this.isFisrt=n;		
	}
	public boolean getIsFirtst(){
		
		return isFisrt;
		
	}
	public void setCurrent_position(int mCurrent) {
             this.curren_Position=mCurrent;		
	}
	public int getCurrentPosition() {
		
         return   curren_Position;
 
	}
	public void setProduct_id(String string) {
		this.Product_Id=string;
		
	}
	public String getProduct_id(){
		return Product_Id;}
	
	
	
}