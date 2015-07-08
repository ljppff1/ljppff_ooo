package com.james.mshop;

import android.app.Application;

public class MyApplication extends Application {
   private boolean isLogined;

@Override
public void onCreate() {
	super.onCreate();
}

public void setLoginTag(boolean isLogined) {
              this.isLogined=isLogined;	
}
public boolean getLoginTag(){
	return isLogined;
};
          
}
