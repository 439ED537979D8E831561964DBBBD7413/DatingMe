package com.acemurder.datingme.login;

import com.acemurder.datingme.config.Const;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by zhengyuxuan on 16/8/7.
 */

public class LoginModel implements LoginContract.ILoginModel {

    private LoginContract.ILoginCallBack mCallBack;

    public LoginModel(LoginContract.ILoginCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public void login(String id, String password) {
        AVUser.loginByMobilePhoneNumberInBackground(id, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null)
                    mCallBack.onLoginSuccess();
                else {
                    switch (e.getCode()){
                        case Const.PASSWORD_WRONG:
                            mCallBack.onLoginError(Const.PASSWORD_WRONG);
                            break;
                        case Const.USER_NOT_EXIST:
                            mCallBack.onLoginError(Const.USER_NOT_EXIST);
                            break;
                        default:
                            mCallBack.onLoginError(Const.UNKONWN_WRONG);
                            break;
                    }

                }
            }
        });
    }

    @Override
    public void signIn(String id, String password) {
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(id);// 设置用户名
        user.setPassword(password);// 设置密码
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    mCallBack.onSignInSuccess();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    switch (e.getCode()){
                        case Const.HAS_SAME_NAME:
                            mCallBack.onSignError(Const.HAS_SAME_NAME);
                            break;
                        default:
                            mCallBack.onSignError(Const.UNKONWN_WRONG);
                            break;
                    }
                }
            }
        });
    }
}
