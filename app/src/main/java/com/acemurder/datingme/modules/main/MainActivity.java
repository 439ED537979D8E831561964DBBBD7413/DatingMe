package com.acemurder.datingme.modules.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.acemurder.datingme.R;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String colors[] = new String[]{"#FF9100","#304FFE","#FF5252","#00C853"};
    private MyFragmentPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (APP.getAVUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }*/

        initView();
    }

    private void initView() {
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(mPagerAdapter);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_date), Color.parseColor(colors[0])).title("约定").
                build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_community), Color.parseColor(colors[1])).title("社区")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_instant),Color.parseColor(colors[2])).title("通讯")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_person),Color.parseColor(colors[3])).title("个人").
                build());
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager,2);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setBgColor(Color.BLACK);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(20);
        navigationTabBar.setBehaviorEnabled(true);
    }

}
