package com.acemurder.datingme.modules.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.util.LogUtils;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.RequestBody;

import static android.R.attr.y;
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
        initView();
    }

    private void testData() {
       /* DatingItem item = new DatingItem();
        item.setTheme("打篮球");
        item.setContent("早上八点,一起来打篮球啊");
        item.setPhotoSrc("http://www.qiniu.com/public/v12/img/feature/pic-safety.png");
        item.setPromulgator("acemurder");*/
        /*try {
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(item.toString())).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        // Log.e("=======",item.toString().getBytes("utf-8"));

        List<String> list = new ArrayList<>();
        list.add("www.baidu.com");
        list.add("www.taobao.com");
        list.add("www.tencent.com");
        Community community = new Community();
        community.setAuthorName("acemurder");
        community.setPhotoSrc(list);
        community.setTitle("真开心``````");
        community.setContent("hahahahha嘿嘿嘿");
        Log.e("-----",community.toString());
        RequestManager.INSTANCE.addCommunityItem(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(Response response) {
                super.onNext(response);
                Log.e("-----",response.toString());

            }
        }),community.toString());

    }

    private void initView() {
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(mPagerAdapter);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.mipmap.ic_launcher), Color.GRAY).title("约定")
        .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.mipmap.ic_launcher), Color.GRAY).title("社区")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.mipmap.ic_launcher), Color.GRAY).title("通讯")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.mipmap.ic_launcher), Color.GRAY).title("约定")
                .build());
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager,2);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
//        navigationTabBar.setTypeface("fonts/custom_font.ttf");
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
