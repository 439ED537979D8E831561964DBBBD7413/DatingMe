package com.acemurder.datingme.modules.dating;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.acemurder.datingme.data.bean.DatingItem;
import com.avos.avoscloud.AVUser;

/**
 * Created by fg on 2016/8/18.
 */
public interface EditContract {
    interface IEditView extends IBaseView<IEditPresenter>{
        void initView();
        void finishActivity();
    }

    interface IEditPresenter extends IBasePresenter<IEditView>{
        void sendDatingItem(DatingItem datingItem);
        void getDatingItems(int page,int size);
        AVUser getUserInfo(String id);
    }
}
