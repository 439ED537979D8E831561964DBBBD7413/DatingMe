package com.acemurder.datingme.modules.dating;

import android.support.v4.app.Fragment;

import com.acemurder.datingme.data.bean.DatingItem;

import java.util.List;

/**
 * Created by fg on 2016/8/16.
 */
public class DatingFragment extends Fragment implements DatingContract.IDatingView{



    @Override
    public void showData(List<DatingItem> datingItems) {

    }

    @Override
    public void showLoadError() {

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
}
