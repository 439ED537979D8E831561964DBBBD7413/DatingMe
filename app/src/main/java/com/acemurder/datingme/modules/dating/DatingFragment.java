package com.acemurder.datingme.modules.dating;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.onRcvScrollListener;
import com.acemurder.datingme.component.widget.DividerItemDecoration;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.modules.dating.event.DatingItemTypeChangeEvent;
import com.acemurder.datingme.modules.dating.event.InsertDatingEvent;
import com.acemurder.datingme.util.AnimationUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.avos.avoscloud.Messages.StatusType.on;

/**
 * Created by fg on 2016/8/16.
 */
public class DatingFragment extends Fragment implements DatingContract.IDatingView {
    @BindView(R.id.recycler_view_dating)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private Unbinder mUnbinder;
    private DatingAdapter mDatingAdapter;
    private DatingPresenter mDatingPresenter;
    private int page = 0;
    private boolean hasMore = true;
    private boolean isOnlyNotDating = false;
    private List<DatingItem> mDatingItemList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dating, container, false);
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
        mDatingPresenter = new DatingPresenter(getActivity(), this);
        initView();
        AnimationUtils.hideFabInRecyclerView(mRecyclerView,fab);
        mDatingPresenter.getDatingItems(page, 10);

    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(getActivity(), AddDatingActivity.class);
        startActivityForResult(intent, 1);
    }


    private void initView() {

        mSwipeRefreshLayout.setOnRefreshListener(() ->{
            //mDatingItemList.clear();
            Log.e("-------","cdscdscdscds");
            getItem(page = 0);
            hasMore = true;
        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatingAdapter = new DatingAdapter(mDatingItemList, getActivity());
        mRecyclerView.setAdapter(mDatingAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener() {
            @Override
            public void onBottom() {
                if (!mSwipeRefreshLayout.isRefreshing() && hasMore)
                    getItem(++page);
            }
        });



    }

    public void getItem(int page) {
        mSwipeRefreshLayout.setRefreshing(true);
        if (isOnlyNotDating)
            mDatingPresenter.getDatingItems(page, 10,true);
        else
            mDatingPresenter.getDatingItems(page,10);
    }

    @Override
    public void showData(List<DatingItem> datingItems) {
        Log.e("showData",datingItems.get(0).getContent());
        if (page == 0){
            mDatingItemList.clear();
        }
        mDatingItemList.addAll(datingItems);

        mDatingAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);

    }



    @Override
    public void showLoadError() {
        mSwipeRefreshLayout.setRefreshing(false);
        // Toast.makeText(APP.getContext(), "抱歉，加载数据失败", Toast.LENGTH_SHORT).show();
      //  Snackbar.make(mRecyclerView, "网络有点小问题", Snackbar.LENGTH_SHORT).show();

        Snackbar sBar = Snackbar.make(mRecyclerView, "网络有点小问题", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout)sBar.getView();
        ve.setBackgroundColor(Color.parseColor("#DEAE75"));
        ve.setAlpha(0.5f);
        ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
        sBar.show();
    }

    @Override
    public void showNoMore() {
       // Toast.makeText(getActivity(), "已是最新数据", Toast.LENGTH_SHORT).show();
        hasMore = false;
        Snackbar sBar = Snackbar.make(mRecyclerView, "没有更多了哦", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout ve = (Snackbar.SnackbarLayout)sBar.getView();
        ve.setBackgroundColor(Color.parseColor("#DEAE75"));
     //   ve.setAlpha(0.5f);
        ((TextView) ve.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FFFFFF"));
        sBar.show();        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void setPresenter(DatingContract.IDatingPresenter presenter) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInserDatingItemEvent(InsertDatingEvent insertDatingEvent){
        Log.e("=========","mmmmmmmmmmmmmmmmm");
      //  mDatingItemList.clear();
        getItem(page = 0);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        mDatingPresenter.unBind();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDatingItemTypeChangeEvent(DatingItemTypeChangeEvent event){
        if (event.isOnlyNotDating()){
            isOnlyNotDating = true;
            getItem(page = 0);
        }else {
            isOnlyNotDating = false;
            getItem(page = 0);
        }
    }

}
