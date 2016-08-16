package com.acemurder.datingme.modules.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

import java.util.ArrayList;
import java.util.List;

import static rx.schedulers.Schedulers.test;

public class MainActivity extends AppCompatActivity {

    private MyFragmentPagerAdapter mPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (APP.getAVUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }*/
       // initView();
         testData();
    }

    private void testData() {
        RequestManager.INSTANCE.getDatingItems(new SimpleSubscriber<List<DatingItem>>(this,
                new SubscriberListener<List<DatingItem>>() {
                    @Override
                    public void onNext(List<DatingItem> datingItems) {
                        super.onNext(datingItems);
                        for (DatingItem item:datingItems){
                            Log.e("=========",item.getContent());
                        }
                    }
                }),10,0);
    }

    private void initView() {
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(mPagerAdapter);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_toys_white_24px), Color.GRAY).title("约定")
        .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_public_white_24px), Color.GRAY).title("社区")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_chat_white_24px), Color.GRAY).title("通讯")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_person_pin_white_24px), Color.GRAY).title("约定")
                .build());
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
        navigationTabBar.setTitleSize(10);
        navigationTabBar.setBehaviorEnabled(true);
    }

}
