package com.acemurder.datingme.modules.dating;

import android.content.Context;
import android.util.Log;

import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.avos.avoscloud.AVUser;

/**
 * Created by fg on 2016/8/18.
 */
public class EditPresenter implements EditContract.IEditPresenter {
    private EditContract.IEditView mIEditView;
    private Context mContext;

    public EditPresenter(Context context, EditContract.IEditView IEditView){
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
                        mIEditView.finishActivity();

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }),datingItem.toString());
    }

    @Override
    public void getDatingItems(int page, int size) {

    }

    @Override
    public AVUser getUserInfo(String id) {
        return null;
    }

    @Override
    public void bind(EditContract.IEditView view) {

    }

    @Override
    public void unBind() {

    }
}
