package com.acemurder.datingme.modules.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.onRcvScrollListener;
import com.acemurder.datingme.data.bean.Community;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fg on 2016/8/15.
 */
public class CommunityFragment extends Fragment implements CommunityContract.ICommunityView{
    private Unbinder mUnbinder;
    private CommunityAdapter mCommunityAdapter;
    private List<Community> mCommunityList;
    private CommunityPresenter mCommunityPresenter;
    private int page = 0;
    @BindView(R.id.recycler_view_community)RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        mCommunityPresenter = new CommunityPresenter(this,getActivity());
        initView();
        return view;
    }



    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommunityAdapter = new CommunityAdapter(getActivity(),mCommunityList);
        //这里的mCommunityList还没有东西，所以我getItem返回的是1
        mRecyclerView.setAdapter(mCommunityAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener(){
            @Override
            public void onBottom() {
                getItem(++page);
            }
        });


    }
    public void getItem(int page) {
        mCommunityPresenter.getCommunityItems(page,3);
    }

    @Override
    public void showCommunityItems(List<Community> communities) {
            mCommunityList.addAll(communities);
            mCommunityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGetCommunityItemsError() {

    }


    @Override
    public void setPresenter(CommunityContract.ICommunityPresenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
