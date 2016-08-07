package com.acemurder.datingme.login;

import android.os.Bundle;

import com.acemurder.datingme.base.BaseActivity;
import com.acemurder.datingme.R;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView{

    private LoginContract.ILoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenter(this);

    }

    @Override
    public void showLoginError(int type) {

    }

    @Override
    public void showSignInError(int type) {

    }

    @Override
    public void showLoginSuccess() {

    }

    @Override
    public void showSignInSuccess() {

    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {

    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.unBind();
        super.onDestroy();
    }
}
