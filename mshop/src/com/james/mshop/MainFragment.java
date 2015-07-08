package com.james.mshop;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import com.webdesign688.mshop.R;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainFragment extends Fragment
{

	private int mColorRes = -1;
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View layout = inflater.inflate(R.layout.fragment_tabs_pager, null);
		mTabHost = (TabHost) layout.findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) layout.findViewById(R.id.pager_tabs);

		mTabsAdapter = new TabsAdapter(getActivity(), mTabHost, mViewPager);
		View view1=getActivity().getLayoutInflater().inflate(R.layout.item_tab1, null);
		TabSpec setIndicator1 = mTabHost.newTabSpec("jingxuan").setIndicator(view1);
		mTabsAdapter.addTab(
				setIndicator1,
				Tab1MenuFrame1.class, null);
		View view2=getActivity().getLayoutInflater().inflate(R.layout.item_tab2, null);
		
		TabSpec setIndicator2 = mTabHost.newTabSpec("allfourm")
				.setIndicator(view2);
		mTabsAdapter.addTab(setIndicator2, Tab2MenuFrame2.class, null);
		View view3=getActivity().getLayoutInflater().inflate(R.layout.item_tab3, null);
		TabSpec setIndicator3 = mTabHost.newTabSpec("allfourm")
				.setIndicator(view3);
		mTabsAdapter.addTab(setIndicator3, Tab3MenuFrame3.class, null);

		if (savedInstanceState != null)
		{
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", mColorRes);
	}

	public class TabsAdapter extends FragmentStatePagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener
	{
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		final class TabInfo
		{
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args)
			{
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		class DummyTabFactory implements TabHost.TabContentFactory
		{
			private final Context mContext;

			public DummyTabFactory(Context context)
			{
				mContext = context;
			}

			@Override
			public View createTabContent(String tag)
			{
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager)
		{
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args)
		{
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount()
		{
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position)
		{
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId)
		{
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels)
		{
		}

		@Override
		public void onPageSelected(int position)
		{
			Tab1Activity activity = (Tab1Activity) MainFragment.this
					.getActivity();
			if (position == 0)
			{
				activity.getSlidingMenu().setTouchModeAbove(
						SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
			else
			{
				activity.getSlidingMenu().setTouchModeAbove(
						SlidingMenu.TOUCHMODE_MARGIN);
			}

			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);

		}

		@Override
		public void onPageScrollStateChanged(int state)
		{
		}
	}

}
