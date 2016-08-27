package com.acemurder.datingme.modules.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSingleChatActivity;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Looper.getMainLooper;

public class MyDateActivity extends AppCompatActivity {

    @BindView(R.id.my_date_rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_date_sr_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private MyDateAdapter mDateAdapter;
    private List<DatingItem> mDatingItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_date);
        ButterKnife.bind(this);
        init();
        //RequestManager.INSTANCE.
    }

    private void init() {
        mToolbar.setTitle("我的约");

        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        mToolbar.setNavigationOnClickListener((view -> onBackPressed()));
        mDateAdapter = new MyDateAdapter(this,mDatingItems);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItems();
            }

        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mDateAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        getItems();
    }


    private void getItems() {
        RequestManager.INSTANCE.getDatingItemsWithName(new SimpleSubscriber<List<DatingItem>>(this, new SubscriberListener<List<DatingItem>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar sBar = Snackbar.make(mRecyclerView, "网络有点小问题", Snackbar.LENGTH_SHORT);
                Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout)sBar.getView();
                ve.setBackgroundColor(Color.parseColor("#DEAE75"));
                ve.setAlpha(0.5f);
                ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
                sBar.show();

            }

            @Override
            public void onNext(List<DatingItem> datingItems) {
                super.onNext(datingItems);
                if (datingItems.size() != 0){
                    mDatingItems.clear();
                    mSwipeRefreshLayout.setRefreshing(false);
                    mDatingItems.addAll(datingItems);
                    mDateAdapter.notifyDataSetChanged();
                }else{
                    Handler handler = new Handler(getMainLooper());
                    handler.post(() -> new MaterialDialog.Builder(MyDateActivity.this)
                            .title("还没有发布过约哦")
                            .content("居然还没有约过,快去发布一条约吧?")
                            .canceledOnTouchOutside(false)
                            .titleColor(Color.parseColor("#F7C282"))
                            .contentColor(Color.parseColor("#F7C282"))
                           // .positiveText("吐槽")
                            .negativeText("好的")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            }).show());
                }

            }
        }), APP.getAVUser().getUsername());
    }
}
