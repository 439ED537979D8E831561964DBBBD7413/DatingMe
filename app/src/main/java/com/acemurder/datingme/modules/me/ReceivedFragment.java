package com.acemurder.datingme.modules.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acemurder.datingme.R;

/**
 * Created by fg on 2016/8/25.
 */
public class ReceivedFragment extends Fragment implements PersonalContract.IReceivedView{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview,container,false);
        return view;
    }

    @Override
    public void showReceivedDatingItems() {

    }

    @Override
    public void showGetReceivedDatingItemsError() {

    }

    @Override
    public void setPresenter(PersonalContract.IReceivedPresenter presenter) {

    }
}
