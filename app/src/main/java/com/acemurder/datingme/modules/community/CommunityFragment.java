package com.acemurder.datingme.modules.community;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.onRcvScrollListener;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.modules.community.adapter.CommunityAdapter;
import com.acemurder.datingme.modules.community.event.CommunityInsertEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fg on 2016/8/15.
 */
public class CommunityFragment extends Fragment implements CommunityContract.ICommunityView {
    private Unbinder mUnbinder;
    private CommunityAdapter mCommunityAdapter;
    private List<Community> mCommunityList = new ArrayList<>();
    private CommunityPresenter mCommunityPresenter;
    private int page = 0;
    @BindView(R.id.recycler_view_community)
    RecyclerView mRecyclerView;
    @BindView(R.id.community_sr_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean hasMore = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCommunityPresenter = new CommunityPresenter(this, getActivity());
        initView();
        mCommunityPresenter.getCommunityItems(0, 10);        //   mCommunityPresenter.getCommunityItems(page, 4);

    }

    private void initView() {

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // mSwipeRefreshLayout.setRefreshing(true);
            getItem(page = 0);
            hasMore = true;


        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommunityAdapter = new CommunityAdapter(getActivity(), mCommunityList);
        mRecyclerView.setAdapter(mCommunityAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener() {
            @Override
            public void onBottom() {
                if (!mSwipeRefreshLayout.isRefreshing() && hasMore)
                    getItem(++page);
            }
        });
    }


    public void getItem(int page) {

        // mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));

        mSwipeRefreshLayout.setRefreshing(true);
        // mDatingPresenter.getDatingItems(page, 10);
        mCommunityPresenter.getCommunityItems(page, 10);


    }



    @Override
    public void showCommunityItems(List<Community> communities) {
        mSwipeRefreshLayout.setRefreshing(false);

        //   Log.e("CommunityFragment", communities.get(0).getContent());
        if (page == 0)
            mCommunityList.clear();
        mCommunityList.addAll(communities);
        mCommunityAdapter.notifyDataSetChanged();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void communityInserEvent(CommunityInsertEvent event){
        Log.e("communityInserEvent","communityInserEvent");
        getItem(page = 0);
    }

    @Override
    public void showGetCommunityItemsError() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showNoMore() {
        mSwipeRefreshLayout.setRefreshing(false);
        hasMore = false;
    }


    @Override
    public void setPresenter(CommunityContract.ICommunityPresenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

