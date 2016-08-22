package com.acemurder.datingme.modules.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.MainViewPager;
import com.acemurder.datingme.component.widget.bottombar.BottomBar;
import com.acemurder.datingme.modules.community.CommunityFragment;
import com.acemurder.datingme.modules.dating.DatingFragment;
import com.acemurder.datingme.modules.im.ContactFragment;
import com.acemurder.datingme.modules.me.PersonalFragment;
import com.acemurder.datingme.util.LogUtils;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class MainActivity extends FragmentActivity {

    //@BindView(R.id.vpPager) ViewPager mViewPager;
    private final String TAG = LogUtils.makeLogTag(this.getClass());
    private DatingFragment mDatingFragment;
    private CommunityFragment mCommunityFragment;
    private PersonalFragment mPersonalFragment;
    private ContactFragment mContactFragment;
    List<Fragment> mFragmentList = new ArrayList<>();

    @BindView(R.id.bottom_bar) BottomBar mBottomBar;
    @BindView(R.id.main_toolbar_title) TextView mTitleTextView;
    @BindView(R.id.main_view_pager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        
    }


    private void initView() {
        mDatingFragment = new DatingFragment();
        mCommunityFragment = new CommunityFragment();
        mPersonalFragment = new PersonalFragment();
        mContactFragment = new ContactFragment();
        mFragmentList.add(mDatingFragment);
        mFragmentList.add(mCommunityFragment);
        mFragmentList.add(mContactFragment);
        mFragmentList.add(mPersonalFragment);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(tabPagerAdapter);
       // mViewPager.setOffscreenPageLimit(4);


        mBottomBar.setOnBottomViewClickListener((view, position) -> {
            mViewPager.setCurrentItem(position,false);
            switch (position) {
                case 0:
                    mTitleTextView.setText("有约");
                    break;
                case 1:
                    mTitleTextView.setText("广场");
                    break;
                case 2:
                    mTitleTextView.setText("我的");
                    break;
                case 3:
                    mTitleTextView.setText("聊聊");
                    break;

                default:
                    break;
            }
        });


    }

}
