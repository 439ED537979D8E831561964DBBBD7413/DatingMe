package com.acemurder.datingme.modules.community;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        mUnbinder = ButterKnife.bind(this, view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCommunityPresenter = new CommunityPresenter(this, getActivity());
        mCommunityPresenter.getCommunityItems(0, 10);        //   mCommunityPresenter.getCommunityItems(page, 4);
        initView();
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
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // mSwipeRefreshLayout.setRefreshing(true);
            page = 0;
            getItem(0);

        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCommunityAdapter = new CommunityAdapter(getActivity(), mCommunityList);
        mRecyclerView.setAdapter(mCommunityAdapter);
        mRecyclerView.addOnScrollListener(new onRcvScrollListener() {
            @Override
            public void onBottom() {

                getItem(++page);
            }
        });
    }


    public void getItem(int page) {
        if (hasMore && !mSwipeRefreshLayout.isRefreshing()) {
           // mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));

            mCommunityPresenter.getCommunityItems(page, 10);
        }

    }

    /*@Subscribe
    public void onCommunuity(CommunityEvent communityEvent) {
        Log.e("Community", communityEvent.mCommunity.getTitle());
        Log.e("Community", communityEvent.mCommunity.getContent());
        mCommunityPresenter.getCommunityItems(0, 3);
        mCommunityAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void showCommunityItems(List<Community> communities) {
        //   Log.e("CommunityFragment", communities.get(0).getContent());
        if (page == 0)
            mCommunityList.clear();
        int count = communities.size();
        mCommunityList.addAll(communities);
        mCommunityAdapter.notifyItemRangeInserted(count, communities.size());
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void communityInserEvent(CommunityInsertEvent event){
        Handler handler  = new Handler(Looper.getMainLooper());
        handler.post(() ->{
            page = 0;
            mCommunityList.clear();
            getItem(page);
        });
    }

<<<<<<< HEAD
=======
    @Override
    public void showGetCommunityItemsError() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void showNoMore() {
        hasMore = false;
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }
>>>>>>> ca15a169c1a9cf63ac170fca7f6072c7d424e97f


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
