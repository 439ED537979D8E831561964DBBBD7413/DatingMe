package com.acemurder.datingme.modules.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.modules.main.MainActivity;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.util.LogUtils;
import com.avos.avoscloud.AVUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.ILoginView {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.login_et_name)
    EditText nameText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    TextView signupLink;

    private LoginContract.ILoginPresenter mLoginPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);

        mProgressDialog.setMessage("拼命登录中~");
        mProgressDialog.setTitle("登录中");
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }


    public boolean validate() {
        boolean valid = true;

        String email = nameText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty()) {
            nameText.setError("enter a valid email address");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }


    @OnClick(R.id.btn_login)
    public void onLoginClick() {
        if (validate()) {
            mProgressDialog.show();
            mLoginPresenter.startLogin(nameText.getText().toString(), passwordText.getText().toString());
        }
    }

    @OnClick(R.id.link_signup)
    public void onLinkSignUpClick() {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    @Override
    public void showLoginError(int type) {
        mProgressDialog.dismiss();
        switch (type) {
            case Const.PASSWORD_WRONG:
                passwordText.requestFocus();
                passwordText.setError("用户名或密码错误");
                break;
            case Const.UNKNOWN_WRONG:
                passwordText.requestFocus();
                passwordText.setError("错误!");
                break;
            case Const.USER_NOT_EXIST:
                nameText.requestFocus();
                nameText.setError("用户不存在!");
                break;
            default:
                break;
        }

    }

    @Override
    public void showSignInError(int type) {

    }

    @Override
    public void showLoginSuccess(AVUser user) {
        LogUtils.LOGE("======>", user.getUsername());
        mProgressDialog.dismiss();
        APP.setUser(user);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

    }

    @Override
    public void showSignInSuccess(AVUser user) {

    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {

    }
}
