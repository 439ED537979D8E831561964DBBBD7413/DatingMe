package com.acemurder.datingme.modules.dating;

import android.content.Context;
import android.util.Log;

import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.avos.avoscloud.AVUser;

import java.io.File;

/**
 * Created by fg on 2016/8/18.
 */
public class EditPresenter implements DatingContract.IEditPresenter {
    private DatingContract.IEditView mIEditView;
    private Context mContext;

    public EditPresenter(Context context, DatingContract.IEditView IEditView){
        mContext = context;
        mIEditView = IEditView;
    }

    @Override
    public void sendDatingItem(DatingItem datingItem) {
        RequestManager.INSTANCE.addDatingItem(new SimpleSubscriber<Response>(mContext,
                new SubscriberListener<Response>() {
                    @Override
                    public void onNext(Response response) {
                        super.onNext(response);
                        Log.e("EP","发送成功" + response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }),datingItem.toString());
    }

    @Override
    public void sendDatingItem(DatingItem datingItem, String path) {
        File file = new File(path);
        if (!file.exists()) {
            sendDatingItem(datingItem);
        } else {
            RequestManager.INSTANCE.addDatingItem(new SimpleSubscriber<Response>(mContext,true,false,
                    new SubscriberListener<Response>() {
                        @Override
                        public void onNext(Response response) {
                            super.onNext(response);
                            mIEditView.showAddSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            mIEditView.showAddError();
                        }
                    }), datingItem, path);
        }


    }


    @Override
    public void bind(DatingContract.IEditView view) {

    }

    @Override
    public void unBind() {
        this.mIEditView = null;
    }
}
