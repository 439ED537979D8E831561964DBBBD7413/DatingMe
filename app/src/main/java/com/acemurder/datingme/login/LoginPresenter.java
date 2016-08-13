package com.acemurder.datingme.login;

import com.acemurder.datingme.base.IBaseView;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.UpdatePasswordCallback;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public class LoginPresenter implements LoginContract.ILoginCallBack, LoginContract.ILoginPresenter {

    private LoginContract.ILoginView mView;
    private LoginContract.ILoginModel mILoginModel;


    public LoginPresenter(LoginContract.ILoginView view) {
        bind(view);
        mILoginModel = new LoginModel(this);
    }

    @Override
    public void onLoginSuccess(AVUser user) {
        mView.showLoginSuccess(user);
    }

    @Override
    public void onSignInSuccess(AVUser user) {
        mView.showSignInSuccess(user);
    }

    @Override
    public void onLoginError(int type) {
        mView.showLoginError(type);
    }

    @Override
    public void onSignError(int type) {
        mView.showSignInError(type);
    }

    @Override
    public void startLogin(String id, String password) {
        mILoginModel.login(id, password);
    }

    @Override
    public void startSignIn(String id, String password) {
        mILoginModel.signIn(id, password);
    }


    @Override
    public void bind(LoginContract.ILoginView view) {
        this.mView = view;
    }

    @Override
    public void unBind() {
        this.mView = null;
    }
}
