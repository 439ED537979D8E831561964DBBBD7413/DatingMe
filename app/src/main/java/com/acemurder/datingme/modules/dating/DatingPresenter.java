package com.acemurder.datingme.modules.dating;

import android.content.Context;

import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public class DatingPresenter implements DatingContract.IDatingPresenter {
    private DatingContract.IDatingView mIDatingView;
    private Context mContext;

    public DatingPresenter(Context context, DatingContract.IDatingView IDatingView) {
        bind(IDatingView);
        this.mContext = context;
    }

    @Override
    public void getDatingItems(int page, int size) {
        RequestManager.INSTANCE.getDatingItems(new SimpleSubscriber<List<DatingItem>>(mContext, new SubscriberListener<List<DatingItem>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mIDatingView.showError();
            }

            @Override
            public void onNext(List<DatingItem> datingItems) {
                super.onNext(datingItems);
                if (datingItems.size() == 0)
                    mIDatingView.showNoMore();
                else
                    mIDatingView.showData(datingItems);
            }
        }),size,page * size);
    }

    @Override
    public void sendDatingItem(DatingItem datingItem) {

    }

    @Override
    public AVUser getUserInfo(String id) {
        return null;
    }

    @Override
    public void bind(DatingContract.IDatingView view) {
        this.mIDatingView = view;
    }

    @Override
    public void unBind() {
        if (mIDatingView != null)
            this.mIDatingView = null;
    }
}
