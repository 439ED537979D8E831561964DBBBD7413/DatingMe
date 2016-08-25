package com.acemurder.datingme.modules.me;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fg on 2016/8/25.
 */
public class PersonalFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 3;
    private String[]titles = new String[]{"发布过的约定","接受过的约定","设置"};
    private Context mContext;

    public PersonalFragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:return new PublishedFragment();
           case 1:return new ReceivedFragment();
           case 2:return new ConfigureFragment();
           default:return new ConfigureFragment();
       }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
