package com.acemurder.datingme.modules.login;

import android.content.Intent;
import android.widget.Toast;

import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.modules.main.MainActivity;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import cn.leancloud.chatkit.LCChatKit;

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

        LCChatKit.getInstance().open(user.getObjectId(), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    mView.showLoginSuccess(user);
                } else {
                    mView.showLoginError(Const.UNKNOWN_WRONG);
                }
            }
        });
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
