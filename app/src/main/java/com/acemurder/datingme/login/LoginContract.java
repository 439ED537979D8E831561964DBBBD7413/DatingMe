package com.acemurder.datingme.login;

import com.acemurder.datingme.base.IBasePresenter;
import com.acemurder.datingme.base.IBaseView;
import com.avos.avoscloud.AVUser;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public interface LoginContract {
    interface ILoginView extends IBaseView<ILoginPresenter> {
        void showLoginError(int type);

        void showSignInError(int type);

        void showLoginSuccess(AVUser user);

        void showSignInSuccess(AVUser user);
    }

    interface ILoginPresenter extends IBasePresenter<ILoginView> {
        void startLogin(String id, String password);

        void startSignIn(String id, String password);
    }

    interface ILoginCallBack {
        void onLoginSuccess(AVUser user);

        void onSignInSuccess(AVUser user);

        void onLoginError(int type);

        void onSignError(int type);
    }

    interface ILoginModel {
        void login(String id, String password);

        void signIn(String id, String password);
    }
}
