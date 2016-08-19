package com.acemurder.datingme.modules.community;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.Remark;

import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/17.
 */

public interface CommunityContract {
    interface ICommunityView extends IBaseView<ICommunityPresenter>{
        void showCommunityItems(List<Community> communities);
        void showGetCommunityItemsError();
        void showSendError();
        void showSendSuccess();

    }

    interface IRemarkView extends IBaseView<ICommunityPresenter>{
        void showRemarkItems(List<Remark> remarks);
        void showGetRemarkItemsError();
        void showSendError();
        void showSendSuccess();

    }
    interface ICommunityPresenter extends IBasePresenter<ICommunityView>{
        void getCommunityItems(int page,int size);
        void sendCommunityItem(Community community);

    }

    interface IRemarkPresenter extends IBasePresenter<ICommunityView>{
        void getRemarkItem(String communityId);
        void sendRemarkItem(Remark remark);
    }


}