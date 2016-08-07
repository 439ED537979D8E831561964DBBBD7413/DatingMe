package com.acemurder.datingme.login;

import android.view.ViewOutlineProvider;

import com.acemurder.datingme.IBasePresenter;
import com.acemurder.datingme.IBaseView;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public interface LoginContract {
    interface ILoginView extends IBaseView{
        void showLoginError(int type);
        void showSignInError(int type);
        void showLoginSucess();
        void showSignInSucess();
    }
    interface ILoginPresenter extends IBasePresenter{
        void startLogin(String id, String password);
        void startSignIn(String id, String password);
    }

    interface ILoginCallBack{
        void onLoginSuccess();
        void onSignInSuccess();
        void onLoginError(int type);
        void onSignError(int type);
    }

    interface ILoginModel{
        void login(String id,String password);
        void signIn(String id,String password);
    }
}
