package com.acemurder.datingme.modules.dating;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.DividerItemDecoration;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.onRcvScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fg on 2016/8/16.
 */
public class DatingFragment extends Fragment implements DatingContract.IDatingView{
    @BindView(R.id.recycler_view)RecyclerView mRecyclerView;
    private Unbinder mUnbinder;
    DatingAdapter mDatingAdapter;
    private DatingPresenter mDatingPresenter;
    private int page = 0;
    private List<DatingItem>mDatingItemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_date,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        mDatingPresenter = new DatingPresenter(getActivity(),this);
        mDatingPresenter.getDatingItems(page,3);
        initView();
        return view;
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDatingAdapter = new DatingAdapter(mDatingItemList,getActivity());
        mRecyclerView.setAdapter(mDatingAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener(){
            @Override
            public void onBottom() {
                Toast.makeText(APP.getContext(), "往下滑加载更多数据", Toast.LENGTH_SHORT).show();
                getItem(++page);
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

    @Override
    public void showLoadError() {
        Toast.makeText(APP.getContext(), "抱歉，加载数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void showAddSuccess() {

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
}
