package com.acemurder.datingme.modules.dating;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.onRcvScrollListener;
import com.acemurder.datingme.component.widget.DividerItemDecoration;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.dating.event.InsertDatingEvent;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PersonDatingActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_dating)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private String name;
    private DatingAdapter mDatingAdapter;

    // @BindView(R.id.search_view) MaterialSearchView mMaterialSearchView;

    private List<DatingItem> mDatingItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_dating);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("name");
        if (name == null)
            onBackPressed();
        init();
        getItem();


    }

    private void init() {
        mToolbar.setTitle(name+"发布的约");

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        mToolbar.setNavigationOnClickListener((view -> onBackPressed()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatingAdapter = new DatingAdapter(mDatingItemList,this,false);
        mRecyclerView.setAdapter(mDatingAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(() ->{
            getItem();
        });

    }


    public void getItem(){
        mSwipeRefreshLayout.setRefreshing(true);
        RequestManager.INSTANCE.getDatingItemsWithName(new SimpleSubscriber<List<DatingItem>>(this, new SubscriberListener<List<DatingItem>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(List<DatingItem> datingItems) {
                super.onNext(datingItems);
                if (datingItems.size() == 0){}
                else{
                    mDatingItemList.clear();
                    mDatingItemList.addAll(datingItems);
                    mDatingAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }),name);
    }

}