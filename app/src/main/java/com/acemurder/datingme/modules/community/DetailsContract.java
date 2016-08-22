package com.acemurder.datingme.modules.community;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.Remark;

import java.util.List;

/**
 * Created by fg on 2016/8/23.
 */
public interface DetailsContract {
    interface IDetailsView extends IBaseView<CommunityContract.ICommunityPresenter> {
        void showCommunityItems(List<Community> communities);
        void showGetCommunityItemsError();
    }
    interface IRemarkView extends IBaseView<IDetailsPresenter>{
        void showRemarkItems(List<Remark> remarks);
        void showGetRemarkItemsError();
        void showSendError();
        void showSendSuccess();

    }

    interface IDetailsPresenter extends IBasePresenter<IDetailsView>{
        void getDetailsItem(int page,int size);
    }

}
