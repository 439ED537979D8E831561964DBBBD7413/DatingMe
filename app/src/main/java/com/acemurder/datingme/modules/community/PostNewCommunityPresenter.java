package com.acemurder.datingme.modules.community;

import android.content.Context;
import android.util.Log;

import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;

import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/20.
 */

public class PostNewCommunityPresenter implements CommunityContract.IPostNewCommunityPresenter{

    private Context mContext;
    private CommunityContract.IPostNewCommunityView mView;

    public PostNewCommunityPresenter(Context context, CommunityContract.IPostNewCommunityView view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void sendCommunityItem(Community community) {
        RequestManager.INSTANCE.addCommunityItem(new SimpleSubscriber<Response>(mContext, true,false,new SubscriberListener<Response>() {
            @Override
            public void onNext(Response response) {
                super.onNext(response);
                mView.showSendSuccess();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showSendError();
            }
        }),community);
    }

    @Override
    public void sendCommunityItem(Community community, List<String> path) {
        RequestManager.INSTANCE.addCommunityItem(new SimpleSubscriber<Response>(mContext, true, false, new SubscriberListener<Response>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.showSendError();
            }

            @Override
            public void onNext(Response response) {
                super.onNext(response);
                mView.showSendSuccess();
            }
        }),community,path);
    }

    @Override
    public void bind(CommunityContract.IPostNewCommunityView view) {

    }

    @Override
    public void unBind() {
        this.mView = null;
    }
}
