package com.acemurder.datingme.modules.community;

import android.content.Context;

import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;

import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/17.
 */

public class CommunityPresenter implements CommunityContract.ICommunityPresenter {

    private CommunityContract.ICommunityView mICommunityView;
    private Context mContext;


    public CommunityPresenter(CommunityContract.ICommunityView ICommunityView, Context context) {
        mICommunityView = ICommunityView;
        mContext = context;
    }

    @Override
    public void getCommunityItems(int page, int size) {
        RequestManager.INSTANCE.getCommunityItems(new SimpleSubscriber<List<Community>>(mContext,
                new SubscriberListener<List<Community>>() {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mICommunityView.showGetCommunityItemsError();
                    }

                    @Override
                    public void onNext(List<Community> communities) {
                        super.onNext(communities);
                        mICommunityView.showCommunityItems(communities);
                    }
                }),size,page * size);
    }

    @Override
    public void sendCommunityItem(Community community) {
        RequestManager.INSTANCE.addCommunityItem(new SimpleSubscriber<Response>(mContext, new SubscriberListener<Response>() {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
                mICommunityView.showSendSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mICommunityView.showSendError();
            }
        }),community.toString());
    }

    @Override
    public void bind(CommunityContract.ICommunityView view) {

    }

    @Override
    public void unBind() {
        this.mICommunityView = null;
    }
}
