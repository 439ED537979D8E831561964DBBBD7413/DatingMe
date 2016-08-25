package com.acemurder.datingme.modules.me;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.acemurder.datingme.data.bean.User;

import java.util.List;

/**
 * Created by fg on 2016/8/24.
 */
public interface PersonalContract {
    interface IConfigureView extends IBaseView<IConfigurePresenter>{
        void showPersonalDetails();
        void showGetPersonalDetailsError();
        void showSendError();
        void showSendSuccess();
    }

    interface IConfigurePresenter extends IBasePresenter<IConfigureView>{
        void getPersonalDetails(String objectId);
        void sendDetailsItem(User user);
        void sendDetailsItem(User user,List<String> path);
    }

    interface IPublishedView extends IBaseView<IPublishedPresenter>{
        void showPublishedDatingItems();
        void showGetPublishedDatingItemsError();
    }

    interface IPublishedPresenter extends IBasePresenter<IPublishedView>{
        void getPublishedDatingItems(String objectId);
    }

    interface IReceivedView extends IBaseView<IReceivedPresenter>{
        void showReceivedDatingItems();
        void showGetReceivedDatingItemsError();
    }

    interface IReceivedPresenter extends IBasePresenter<IReceivedView>{
        void getReceivedDatingItems();
    }
}
