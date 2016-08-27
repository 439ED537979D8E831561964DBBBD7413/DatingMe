package com.acemurder.datingme.modules.dating;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.acemurder.datingme.data.bean.DatingItem;
import com.avos.avoscloud.AVUser;

import java.util.List;


/**
 * Created by zhengyuxuan on 16/8/13.
 */

public interface DatingContract {
    interface IDatingPresenter extends IBasePresenter<IDatingView>{
        void getDatingItems(int page,int size);
        void getDatingItems(int page,int size,boolean is);

        //   void sendDatingItem(DatingItem datingItem);
        void date(DatingItem datingItem);

    }

    interface IDatingView extends IBaseView<IDatingPresenter>{
        void showData(List<DatingItem> datingItems);
        void showLoadError();
        void showNoMore();

    }

    interface IEditView extends IBaseView<DatingContract.IEditPresenter>{
        void showAddSuccess();
        void showAddError();
    }

    interface IEditPresenter extends IBasePresenter<DatingContract.IEditView>{
        void sendDatingItem(DatingItem datingItem);
        void sendDatingItem(DatingItem datingItem,String path);
    }


}
