package com.acemurder.datingme.modules.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.User;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.im.guide.AVImClientManager;
import com.acemurder.datingme.modules.main.MainActivity;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.modules.me.SettingActivity;
import com.acemurder.datingme.util.LogUtils;
import com.acemurder.datingme.util.Utils;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import java.util.List;

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

        /*RequestManager.INSTANCE.getDatingItems(new SimpleSubscriber<List<DatingItem>>(this, new SubscriberListener<List<DatingItem>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(List<DatingItem> datingItems) {
                super.onNext(datingItems);
                for (DatingItem d : datingItems){
                    Log.e("------",d.getMaster().getPhotoSrc());
                }
            }
        }),100,0,"master");*/




      /*  User user = new User();
        user.setObjectId("57c018f85bbb50006327ecb7");
        DatingItem datingItem = new DatingItem();
        datingItem.setContent("hahahhaha");
        datingItem.setTheme("哈哈哈哈");
        datingItem.setPromulgator("acemurder");
        datingItem.setMaster(user);

        RequestManager.INSTANCE.addDatingItem(new SimpleSubscriber<Response>(this, new SubscriberListener<Response>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(Response response) {
                super.onNext(response);
                Log.e(response.toString(),response.toString());
            }
        }),datingItem.toString());*/

        mLoginPresenter = new LoginPresenter(this);
        mProgressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);

        mProgressDialog.setMessage("拼命登录中~");
        mProgressDialog.setTitle("登录中");
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (APP.hasLogin()){
            nameText.setText(APP.getAVUser().getUsername());
            passwordText.setText("**********");
            mProgressDialog.show();
            AVImClientManager.getInstance().open(APP.getAVUser().getUsername(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {

                    if (e == null){
                        mProgressDialog.dismiss();
                        passwordText.clearFocus();
                        nameText.clearFocus();

                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Utils.hideSoftInput(passwordText);
                        Utils.hideSoftInput(nameText);
                        LoginActivity.this.finish();
                    }
                }
            });

        }

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
            nameText.setError("得输入你的名字呀");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            passwordText.setError("密码得4到16位哦");
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
        Utils.hideSoftInput(passwordText);
        Utils.hideSoftInput(nameText);

        LogUtils.LOGE("======>", user.getUsername());
        mProgressDialog.dismiss();
        APP.setUser(user);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.finish();

    }

    @Override
    public void showSignInSuccess(AVUser user) {

    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {

    }
}
