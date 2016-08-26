package com.acemurder.datingme.modules.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fg on 2016/8/15.
 */
public class PersonalFragment extends Fragment {
    private Unbinder mUnbinder;
    private PersonalFragmentPagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar)Toolbar mToolbar;
    @BindView(R.id.fragment_person_vPager)ViewPager mViewPager;
    @BindView(R.id.fragment_person_tab_layout)TabLayout mTabLayout;
    @BindView(R.id.fragment_person_portrait)CircleImageView mCircleImageView;
    @BindView(R.id.fragment_person_tv_name)TextView nameView;
    @BindView(R.id.fragment_person_tv_sex)TextView sexView;
    @BindView(R.id.fragment_person_signature_content)TextView signatureView;
    @BindView(R.id.app_bar)AppBarLayout mAppBarLayout;
    /*@BindView(R.id.fragment_person_tv_publish)TextView publishView;
    @BindView(R.id.fragment_person_tv_finished)TextView finishView;
    @BindView(R.id.pia_chart)PieChart mPieChartView;
    @BindView(R.id.fragment_person_configure)TextView configureView;
    @BindView(R.id.fragment_person_about)TextView aboutView;*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initView();
        return view;
    }

    private void initView() {
        mPagerAdapter = new PersonalFragmentPagerAdapter(getActivity().getSupportFragmentManager(),getActivity());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
