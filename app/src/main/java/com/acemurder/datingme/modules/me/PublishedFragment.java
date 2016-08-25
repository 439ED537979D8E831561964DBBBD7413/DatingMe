package com.acemurder.datingme.modules.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fg on 2016/8/25.
 */
public class PublishedFragment extends Fragment implements PersonalContract.IPublishedView{
    @BindView(R.id.fragment_recycler_view)RecyclerView mRecyclerView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void showPublishedDatingItems() {

    }

    @Override
    public void showGetPublishedDatingItemsError() {

    }

    @Override
    public void setPresenter(PersonalContract.IPublishedPresenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
