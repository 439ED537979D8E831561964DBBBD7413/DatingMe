package com.acemurder.datingme.login;

import com.acemurder.datingme.IBaseView;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public class LoginPresenter implements LoginContract.ILoginCallBack , LoginContract.ILoginPresenter {

    private LoginContract.ILoginView mView;
    private LoginContract.ILoginModel mILoginModel;
    @Override
    public void onLoginSuccess() {
        mView.showLoginSucess();
    }

    @Override
    public void onSignInSuccess() {
        mView.showSignInSucess();
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
        mILoginModel.login(id,password);
    }

    @Override
    public void startSignIn(String id, String password) {
        mILoginModel.signIn(id,password);
    }

    @Override
    public void bind(IBaseView view) {

    }

    @Override
    public void unBind() {

    }
}
