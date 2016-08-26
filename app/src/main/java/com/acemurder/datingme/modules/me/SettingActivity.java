package com.acemurder.datingme.modules.me;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.util.SPUtils;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.os.Looper.getMainLooper;

public class SettingActivity extends AppCompatActivity {



    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @OnClick(R.id.setting_feedback_layout)
    public void onAboutClick(){
        startActivity(new Intent(this,AboutActivity.class));
    }



    @OnClick(R.id.setting_exit_layout)
    public void onQuiteClick() {
        Handler handler = new Handler(getMainLooper());
        handler.post(() -> new MaterialDialog.Builder(this)
                .title("退出当前账号?")
                .content("真的要退出当前账号吗?")
                .titleColor(Color.parseColor("#F7C282"))
                .contentColor(Color.parseColor("#F7C282"))
                .positiveText("退出登录")
                .negativeText("我在看看")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        SPUtils.set(SettingActivity.this, Const.SP_USER_OBJECT_ID, "");
                        SPUtils.set(SettingActivity.this, Const.SP_USER_NAME, "");
                        APP.setHasLogined(false);
                        EventBus.getDefault().post(new ExitEvent());
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        onBackPressed();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).show());
    }

    @OnClick(R.id.setting_remind_layout)
    public void onCleanClick() {
        ProgressDialog progressDialog =  new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);

        progressDialog.setMessage("正在清理");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Glide.get(this).clearMemory();
        Observable observable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                Glide.get(SettingActivity.this).clearDiskCache();
            }
        });
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(SettingActivity.this, "清理失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onNext(Object o) {
                        progressDialog.dismiss();
                        Toast.makeText(SettingActivity.this, "清理完成", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mToolbar.setTitle("设置");

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.btn_navigation_back);
        mToolbar.setNavigationOnClickListener((view -> onBackPressed()));
    }
}
