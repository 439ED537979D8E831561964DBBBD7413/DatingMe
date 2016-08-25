package com.acemurder.datingme.modules.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.modules.main.MainActivity;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.util.permission.Utils;
import com.avos.avoscloud.AVUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity implements LoginContract.ILoginView {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;

    private LoginContract.ILoginPresenter mLoginPresenter;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setTitle("注册中");
    }


    @OnClick(R.id.btn_signup)
    public void onSignInClick() {
        if (validate()) {
            mProgressDialog.show();
            mLoginPresenter.startSignIn(nameText.getText().toString(), passwordText.getText().toString());
        }
    }

    @OnClick(R.id.link_login)
    public void onLinkClick() {
        onBackPressed();
    }


    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            passwordText.setError("between 4 and 16 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }


    @Override
    public void showLoginError(int type) {

    }

    @Override
    public void showSignInError(int type) {
        if (type == Const.HAS_SAME_NAME) {
            nameText.requestFocus();
            nameText.setError("用户名已存在!");
        } else {
            passwordText.requestFocus();
            passwordText.setError("未知错误");
        }
        mProgressDialog.dismiss();
    }

    @Override
    public void showLoginSuccess(AVUser user) {

    }

    @Override
    public void showSignInSuccess(AVUser user) {
        Utils.hideSoftInput(passwordText);

        mProgressDialog.dismiss();
       // APP.setUser(user);
       // startActivity(new Intent(SignupActivity.this, MainActivity.class));
        onBackPressed();
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {

    }
}