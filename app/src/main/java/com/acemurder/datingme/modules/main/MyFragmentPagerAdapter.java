package com.acemurder.datingme.modules.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by fg on 2016/8/16.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>mFragments;
    private String[]mTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
