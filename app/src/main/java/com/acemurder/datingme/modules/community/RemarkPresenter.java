package com.acemurder.datingme.modules.community;

import android.content.Context;
import android.util.Log;

import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;

import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/17.
 */

public class RemarkPresenter implements CommunityContract.IRemarkPresenter {
    private CommunityContract.IRemarkView mIRemarkView;
    private Context mContext;

    public RemarkPresenter(Context context , CommunityContract.IRemarkView IRemarkView) {
        mIRemarkView = IRemarkView;
        mContext = context;
    }

    @Override
    public void getRemarkItem(String communityId) {
        RequestManager.INSTANCE.getRemarkItems(new SimpleSubscriber<List<Remark>>(mContext, new SubscriberListener<List<Remark>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mIRemarkView.showGetRemarkItemsError();
            }

            @Override
            public void onNext(List<Remark> remarks) {
                super.onNext(remarks);
                Log.e("RemarkPresenter",remarks.toString());
                mIRemarkView.showRemarkItems(remarks);
            }
        }),communityId);
    }

    @Override
    public void sendRemarkItem(Remark remark) {
        RequestManager.INSTANCE.sendRemark(new SimpleSubscriber<Response>(mContext, new SubscriberListener<Response>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mIRemarkView.showSendError();
            }

            @Override
            public void onNext(Response response) {
                super.onNext(response);
                Log.e("RemarkPresenter",response.toString());
                mIRemarkView.showSendSuccess();
            }
        }),remark.toString());
    }

    @Override
    public void bind(CommunityContract.IRemarkView view) {

    }

    @Override
    public void unBind() {
        this.mIRemarkView = null;
    }
}
