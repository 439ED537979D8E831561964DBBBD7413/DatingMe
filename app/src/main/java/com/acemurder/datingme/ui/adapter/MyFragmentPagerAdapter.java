package com.acemurder.datingme.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.acemurder.datingme.ui.fragment.ArrangementFragment;
import com.acemurder.datingme.ui.fragment.CommunityFragment;
import com.acemurder.datingme.ui.fragment.InstantMessageFragment;
import com.acemurder.datingme.ui.fragment.PersonalFragment;

/**
 * Created by fg on 2016/8/16.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 4;
    private String[] titles = new String[]{"约定", "社区", "通讯","我的"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Fragment getItem(int position) {https://github.com/NewCar/DatingMe.git
        switch (position) {
            case 0: return new ArrangementFragment();
            case 1: return new CommunityFragment();
            case 2: return new InstantMessageFragment();
            case 3: return new PersonalFragment();
            default: return new CommunityFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
