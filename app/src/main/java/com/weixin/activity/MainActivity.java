package com.weixin.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weixin.fragment.TabFragment;
import com.weixin.view.TabViewWithText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mViewPager;

    private List<String> titleList = Arrays.asList("first fragment", "second fragment", "third fragment", "fouth fragment");

    private List<Fragment> mFragmentList = new ArrayList<>();

    private TabPagerAdapter mAdapter;

    private List<TabViewWithText> tabList = new ArrayList<>();
    private TabViewWithText mTabOne;
    private TabViewWithText mTabTwo;
    private TabViewWithText mTabThree;
    private TabViewWithText mTabFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        initView();
        initData();
        mAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

    }

    private void initData() {
        for (String title : titleList) {
            TabFragment fragment = new TabFragment();
            Bundle args = new Bundle();
            args.putString(TabFragment.TITLE, title);
            fragment.setArguments(args);
            mFragmentList.add(fragment);
        }

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabOne = (TabViewWithText) findViewById(R.id.tab_one);
        mTabTwo = (TabViewWithText) findViewById(R.id.tab_two);
        mTabThree = (TabViewWithText) findViewById(R.id.tab_three);
        mTabFour = (TabViewWithText) findViewById(R.id.tab_four);

        mTabOne.setOnClickListener(this);
        mTabTwo.setOnClickListener(this);
        mTabThree.setOnClickListener(this);
        mTabFour.setOnClickListener(this);

        mTabOne.setIconAlpha(1.0f);

        tabList.add(mTabOne);
        tabList.add(mTabTwo);
        tabList.add(mTabThree);
        tabList.add(mTabFour);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset>0){
                    TabViewWithText right= tabList.get(position+1);
                    TabViewWithText left=tabList.get(position);

                    left.setIconAlpha(1-positionOffset);
                    right.setIconAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class TabPagerAdapter extends FragmentPagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }


    @Override
    public void onClick(View v) {
        resetAllTabsColor();
        switch (v.getId()) {
            case R.id.tab_one:
                tabList.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_two:
                tabList.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_three:
                tabList.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.tab_four:
                tabList.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    public void resetAllTabsColor(){
        for (TabViewWithText tabView:tabList){
            tabView.setIconAlpha(0);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
