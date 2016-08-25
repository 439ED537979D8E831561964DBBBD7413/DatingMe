package com.acemurder.datingme.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.bottombar.BottomBar;
import com.acemurder.datingme.modules.community.AddCommunityActivity;
import com.acemurder.datingme.modules.community.CommunityFragment;
import com.acemurder.datingme.modules.dating.DatingFragment;
import com.acemurder.datingme.modules.im.ContactFragment;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.modules.me.PersonalFragment;
import com.acemurder.datingme.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity {

    //@BindView(R.id.vpPager) ViewPager mViewPager;
    private final String TAG = LogUtils.makeLogTag(this.getClass());
    private DatingFragment mDatingFragment;
    private CommunityFragment mCommunityFragment;
    private PersonalFragment mPersonalFragment;
   // private LCIMConversationListFragment mLCIMConversationListFragment;
    private ContactFragment mContactFragment;
    List<Fragment> mFragmentList = new ArrayList<>();

    @BindView(R.id.bottom_bar) BottomBar mBottomBar;
    @BindView(R.id.main_toolbar_title) TextView mTitleTextView;
    @BindView(R.id.main_view_pager) ViewPager mViewPager;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    private Menu mMenu;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!APP.hasLogin()){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

        ButterKnife.bind(this);


        initView();
        testData();
    }

    private void testData() {



    }


    private void initView() {

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setOnMenuItemClickListener((menuItem) ->{
            startActivity(new Intent(MainActivity.this,AddCommunityActivity.class));

            return true;
        });


        //mBottomBar.post(() -> hiddenMenu());
        hiddenMenu();
        mDatingFragment = new DatingFragment();
        mCommunityFragment = new CommunityFragment();
        mPersonalFragment = new PersonalFragment();
      //  mLCIMConversationListFragment = new LCIMConversationListFragment();
        mContactFragment = new ContactFragment();
        mFragmentList.add(mDatingFragment);
        mFragmentList.add(mCommunityFragment);
        mFragmentList.add(mContactFragment);
        mFragmentList.add(mPersonalFragment);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),mFragmentList);
        mViewPager.setAdapter(tabPagerAdapter);
       // mViewPager.setOffscreenPageLimit(4);


        mBottomBar.setOnBottomViewClickListener((view, position) -> {
            hiddenMenu();
            mViewPager.setCurrentItem(position,false);
            switch (position) {
                case 0:
                    //hiddenMenu();
                    mTitleTextView.setText("有约");
                    break;
                case 1:
                    mTitleTextView.setText("社区");
                    showMenu();
                    break;
                case 2:
                   // hiddenMenu();
                    mTitleTextView.setText("广场");
                    break;
                case 3:
                   // hiddenMenu();
                    mTitleTextView.setText("我的");
                    break;

                default:
                    break;
            }
        });


    }



    private void hiddenMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(false);
            }
        }
    }

    private void showMenu() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(true);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        hiddenMenu();
        return super.onCreateOptionsMenu(menu);
    }

}
