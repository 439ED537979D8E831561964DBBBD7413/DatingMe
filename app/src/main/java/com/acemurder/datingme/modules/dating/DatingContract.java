package com.acemurder.datingme.modules.dating;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.acemurder.datingme.bean.DatingItem;
import com.avos.avoscloud.AVUser;

import java.util.List;


/**
 * Created by zhengyuxuan on 16/8/13.
 */

public interface DatingContract {
    interface IDatingPresenter extends IBasePresenter<IDatingView>{
        void getDatingItems(int page,int size);
        void sendDatingItem(DatingItem datingItem);
        AVUser getUserInfo(String id);
    }

    interface IDatingView extends IBaseView<IDatingPresenter>{
        void showData(List<DatingItem> datingItems);
        void showError();
        void showNoMore();
    }


}
