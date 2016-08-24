package com.acemurder.datingme.modules.community;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.R;
import com.acemurder.datingme.component.onRcvScrollListener;
import com.acemurder.datingme.data.bean.Community;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
    private List<Community> mCommunityList = new ArrayList<>();
    private CommunityPresenter mCommunityPresenter;
    private int page = 0;
 //  @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view_community)
 RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        mCommunityPresenter = new CommunityPresenter(this,getActivity());
        mCommunityPresenter.getCommunityItems(page,4);
        initView();
        return view;
    }



    private void initView() {
       // mToolbar.setTitle("社区");
     //   mToolbar.setTitleTextColor(Color.WHITE);

       // ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        /*mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.e("CommunityFragment","此处是否被执行");
                if (item.getItemId() == R.id.action_edit){
                    Intent intent = new Intent(getActivity(), WritingActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommunityAdapter = new CommunityAdapter(getActivity(),mCommunityList);
        mRecyclerView.setAdapter(mCommunityAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener(){
            @Override
            public void onBottom() {
                getItem(++page);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    public void getItem(int page) {
        mCommunityPresenter.getCommunityItems(page,3);
    }

    @Subscribe
    public void onCommunuity(CommunityEvent communityEvent){
        Log.e("Community",communityEvent.mCommunity.getTitle());
        Log.e("Community",communityEvent.mCommunity.getContent());
        mCommunityPresenter.getCommunityItems(0,3);
        mCommunityAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCommunityItems(List<Community> communities) {
        Log.e("CommunityFragment",communities.get(0).getContent());
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
