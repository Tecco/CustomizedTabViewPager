package com.tecc0.customizedtabviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    View mIndicator;
    TabWidget mTabWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        mTabWidget = (TabWidget) findViewById(android.R.id.tabs);

        mTabWidget.setStripEnabled(false);
        mTabWidget.setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        mIndicator = findViewById(R.id.indicator);

        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            TextView tv = (TextView) inflater.inflate(R.layout.tab_widget, mTabWidget, false);
            tv.setText(mSectionsPagerAdapter.getPageTitle(i));

            tabHost.addTab(tabHost
                    .newTabSpec(String.valueOf(i))
                    .setIndicator(tv)
                    .setContent(android.R.id.tabcontent));
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mViewPager.setCurrentItem(Integer.valueOf(tabId));
            }
        });

        mViewPager.setOnPageChangeListener(new PageChangeListener());

        final Context self = this;
        ImageView fab = (ImageView)findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(self, "fab clicked !!", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().setElevation(0);

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                case 2:
                    return new Fragment3();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    private class PageChangeListener implements OnPageChangeListener {
        private int scrollingState = ViewPager.SCROLL_STATE_IDLE;

        @Override
        public void onPageSelected(int position) {
            if (scrollingState == ViewPager.SCROLL_STATE_IDLE) {
                updateIndicatorPosition(position, 0);
            }
            mTabWidget.setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollingState = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            updateIndicatorPosition(position, positionOffset);
        }

        private void updateIndicatorPosition(int position, float positionOffset) {
            View tabView = mTabWidget.getChildTabViewAt(position);
            int indicatorWidth = tabView.getWidth();
            int indicatorLeft = (int) ((position + positionOffset) * indicatorWidth);

            final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
            layoutParams.width = indicatorWidth;
            layoutParams.setMargins(indicatorLeft, 0, 0, 0);
            mIndicator.setLayoutParams(layoutParams);
        }
    }
}
