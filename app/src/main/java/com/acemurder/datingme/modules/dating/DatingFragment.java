package com.acemurder.datingme.modules.dating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.onRcvScrollListener;
import com.acemurder.datingme.component.widget.DividerItemDecoration;
import com.acemurder.datingme.data.bean.DatingItem;
import com.baoyz.widget.PullRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/**
 * Created by fg on 2016/8/16.
 */
public class DatingFragment extends Fragment implements DatingContract.IDatingView{
    @BindView(R.id.recycler_view_dating)RecyclerView mRecyclerView;
    @BindView(R.id.swipe_container)PullRefreshLayout mPullRefreshLayout;
   // @BindView(R.id.search_view) MaterialSearchView mMaterialSearchView;
    private Unbinder mUnbinder;
    DatingAdapter mDatingAdapter;
    private DatingPresenter mDatingPresenter;
    private int page = 0;
    private List<DatingItem>mDatingItemList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dating,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        mDatingPresenter = new DatingPresenter(getActivity(),this);
        mDatingPresenter.getDatingItems(page,3);
        initView();
        onClick();
        return view;
    }
    @OnClick(R.id.fab)
    public void onClick(){
        Intent intent = new Intent(getActivity(), EditActivity.class);
        startActivityForResult(intent,1);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:return false;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatingAdapter = new DatingAdapter(mDatingItemList,getActivity());
        mRecyclerView.setAdapter(mDatingAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener(){
            @Override
            public void onBottom() {
                getItem(++page);
            }
        });
        mPullRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        mPullRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDatingPresenter.getDatingItems(page,3);
                Log.e("DatingFragment",page + "");
                mPullRefreshLayout.setRefreshing(false);
            }
        });


    }

    public void getItem(int page) {
        mDatingPresenter.getDatingItems(page,3);
    }

    @Override
    public void showData(List<DatingItem> datingItems) {
        mDatingItemList.addAll(datingItems);
        mDatingAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onEvent(MessageEvent event){
        Log.e("DatingFragment",event.mDatingItem.getContent());
        mDatingPresenter.getDatingItems(0,3);
        mDatingAdapter.notifyDataSetChanged();
    }
    @Override
    public void showLoadError() {
        Toast.makeText(APP.getContext(), "抱歉，加载数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMore() {
        Toast.makeText(getActivity(), "已是最新数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddSuccess() {
        mDatingPresenter.getDatingItems(page,3);
    }

    @Override
    public void showAddError() {

    }

    @Override
    public void setPresenter(DatingContract.IDatingPresenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
