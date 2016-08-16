package com.acemurder.datingme.modules.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.acemurder.datingme.R;
<<<<<<< HEAD
=======
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.util.LogUtils;
>>>>>>> 7e9bbc0cfc3a316e1c60871f4a2e899bbb3908f3
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.List;

import okhttp3.RequestBody;

import static rx.schedulers.Schedulers.test;
>>>>>>> 7e9bbc0cfc3a316e1c60871f4a2e899bbb3908f3

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
<<<<<<< HEAD
        initView();

=======
       // initView();
        testData();
        initView();
    }

    private void testData() {
        DatingItem item = new DatingItem();
        item.setTheme("打篮球");
        item.setContent("早上八点,一起来打篮球啊");
        item.setPhotoSrc("http://www.qiniu.com/public/v12/img/feature/pic-safety.png");
        item.setPromulgator("acemurder");
        /*try {
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(new JSONObject(item.toString())).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        // Log.e("=======",item.toString().getBytes("utf-8"));
        RequestManager.INSTANCE.addDatingItem(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
               // response.toString();
                LogUtils.LOGE("tag",response.getCreatedAt()+"  "+response.getObjectId());
            }
        }),item.toString());
>>>>>>> 7e9bbc0cfc3a316e1c60871f4a2e899bbb3908f3
    }

    private void initView() {
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(mPagerAdapter);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model.Builder(getResources().
<<<<<<< HEAD
                getDrawable(R.drawable.ic_date), Color.parseColor(colors[0])).title("约定")
        .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_community), Color.parseColor(colors[1])).title("社区")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_instant),Color.parseColor(colors[2])).title("通讯")
                .build());
        models.add(new NavigationTabBar.Model.Builder(getResources().
                getDrawable(R.drawable.ic_person),Color.parseColor(colors[3])).title("个人")
=======
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
>>>>>>> 7e9bbc0cfc3a316e1c60871f4a2e899bbb3908f3
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
        navigationTabBar.setTitleSize(20);
        navigationTabBar.setBehaviorEnabled(true);
    }

}
