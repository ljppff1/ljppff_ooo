package com.james.mshop.widgets;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery1 extends Gallery
{
	/**
	 * �������ֵ��������ÿ�ι�������󳤶ȣ�ͼƬ����Ϊ480PX����������600Ч����һЩ�� ���ֵԽ�󣬹����ĳ��Ⱦ�Խ��
	 * Ҳ���ǻ����һ�ι�������Image���������Ƴ��Ⱥ�ÿ�ι���ֻ�ܿ�һ��Image
	 */
	private static final int timerAnimation = 1;
	/*private  boolean islast=false;
	private final Handler mHandler = new Handler()
	{
		

		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case timerAnimation:
				int position = getSelectedItemPosition();
				Log.i("msg", "position:"+position);
				
		
				if (position >= (getCount() - 1)){
					islast =true;
				}
				if(position==0){
					islast =false;
				}
				if (position >= (getCount() - 1)||islast)
				{
					onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
				} else
				{
					onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}
				break;

			default:
				break;
			}
		};
	};*/

/*	private final Timer timer = new Timer();
	private final TimerTask task = new TimerTask()
	{
		public void run()
		{
			mHandler.sendEmptyMessage(timerAnimation);
		}
	};*/

	public MyGallery1(Context paramContext)
	{
		super(paramContext);
		//timer.schedule(task, 6000, 6000);
	}

	public MyGallery1(Context paramContext, AttributeSet paramAttributeSet)
	{
		super(paramContext, paramAttributeSet);
		//timer.schedule(task, 6000, 6000);

	}

	public MyGallery1(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt)
	{
		super(paramContext, paramAttributeSet, paramInt);
		//timer.schedule(task, 6000, 6000);

	}

/*	private boolean isScrollingLeft(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2)
	{
		float f2 = paramMotionEvent2.getX();
		float f1 = paramMotionEvent1.getX();
		if (f2 > f1)
			return true;
		return false;
	}

	public boolean onFling(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
	{
		int keyCode;
		if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2))
		{
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else
		{
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}
*/
	public void destroy()
	{
		//timer.cancel();
	}
}