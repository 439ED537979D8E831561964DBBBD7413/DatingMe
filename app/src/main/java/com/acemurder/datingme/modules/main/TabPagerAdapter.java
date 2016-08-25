package com.acemurder.datingme.modules.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/21.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentsList;

    public TabPagerAdapter(final FragmentManager fm, List<Fragment> fragmentsList) {
        super(fm);
        this.mFragmentsList = fragmentsList;
    }

    @Override
    public Fragment getItem(int position) {
        return (mFragmentsList == null || mFragmentsList.size() == 0) ? null : mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList == null ? 0 : mFragmentsList.size();
    }



}
