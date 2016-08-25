package com.acemurder.datingme.modules.me;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatedMeActivity extends AppCompatActivity {

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
        mToolbar.setTitle("约我的");

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        mToolbar.setNavigationOnClickListener((view -> onBackPressed()));
        mDateAdapter = new MyDateAdapter(this,mDatingItems,false);
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
        RequestManager.INSTANCE.getRecieveDatingItemsWithName(new SimpleSubscriber<List<DatingItem>>(this, new SubscriberListener<List<DatingItem>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mRecyclerView,"网络遇到掉小问题",Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onNext(List<DatingItem> datingItems) {
                super.onNext(datingItems);
                mDatingItems.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                mDatingItems.addAll(datingItems);
                mDateAdapter.notifyDataSetChanged();
            }
        }), APP.getAVUser().getUsername());
    }
}
