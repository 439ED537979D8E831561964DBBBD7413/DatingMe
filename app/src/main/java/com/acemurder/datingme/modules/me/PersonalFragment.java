
package com.acemurder.datingme.modules.me;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acemurder.datingme.APP;
import com.acemurder.datingme.R;
import com.acemurder.datingme.component.widget.CircleImageView;
import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSingleChatActivity;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.modules.main.MainActivity;
import com.acemurder.datingme.util.SPUtils;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Looper.getMainLooper;

/**
 * Created by fg on 2016/8/15.
 */
public class PersonalFragment extends Fragment {
    @BindView(R.id.my_page_edit_layout)
    LinearLayout myPageEditLayout;
    @BindView(R.id.my_page_relate_layout)
    RelativeLayout myPageRelateLayout;
    @BindView(R.id.my_page_trend_layout)
    RelativeLayout  myPageTrendLayout;
    @BindView(R.id.my_page_no_course_layout)
    RelativeLayout  myPageNoCourseLayout;
    @BindView(R.id.my_page_grade_layout)
    RelativeLayout  mContact;

    @BindView(R.id.my_page_setting_layout)
    RelativeLayout  myPageSettingLayout;
    @BindView(R.id.my_page_avatar)
    ImageView myPageAvatar;
    @BindView(R.id.my_page_nick_name)
    TextView        myPageNickName;

    @BindView(R.id.my_page_introduce)
    TextView        myPageIntroduce;


    @OnClick(R.id.my_page_grade_layout)
    public void onContactClick(){
        Handler handler = new Handler(getMainLooper());
        handler.post(() -> new MaterialDialog.Builder(getActivity())
                .title("吐槽一下?")
                .content("向程序猿吐槽一下吗?")
                .titleColor(Color.parseColor("#F7C282"))
                .contentColor(Color.parseColor("#F7C282"))
                .positiveText("吐槽")
                .negativeText("忍一下")
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        Intent intent = new Intent(getActivity(), AVSingleChatActivity.class);
                        intent.putExtra(Constants.MEMBER_ID, "acemurder");
                        getActivity().startActivity(intent);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).show());
    }

    @OnClick(R.id.my_page_setting_layout)
    public void onQuiteClick(){
        Handler handler = new Handler(getMainLooper());
        handler.post(() -> new MaterialDialog.Builder(getActivity())
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
                        SPUtils.set(getActivity(), Const.SP_USER_OBJECT_ID,"");
                        SPUtils.set(getActivity(), Const.SP_USER_NAME,"");
                        APP.setHasLogined(false);
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                }).show());
    }

    @OnClick(R.id.my_page_relate_layout)
    public void onMyDateClick(){
        startActivity(new Intent(getActivity(), MyDateActivity.class));
    }

    @OnClick(R.id.my_page_trend_layout)
    public void onDateMeClick(){
        startActivity(new Intent(getActivity(), DatedMeActivity.class));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myPageNickName.setText(APP.getAVUser().getUsername());
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}