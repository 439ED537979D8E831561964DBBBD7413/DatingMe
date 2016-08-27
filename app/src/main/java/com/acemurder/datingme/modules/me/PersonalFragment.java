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
import android.util.Log;
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
import com.acemurder.datingme.data.bean.User;
import com.acemurder.datingme.data.network.RequestManager;
import com.acemurder.datingme.data.network.subscriber.SimpleSubscriber;
import com.acemurder.datingme.data.network.subscriber.SubscriberListener;
import com.acemurder.datingme.modules.dating.event.InsertDatingEvent;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSingleChatActivity;
import com.acemurder.datingme.modules.login.LoginActivity;
import com.acemurder.datingme.modules.main.MainActivity;
import com.acemurder.datingme.modules.me.event.UserInfoChangeEvent;
import com.acemurder.datingme.util.SPUtils;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.os.Build.VERSION_CODES.M;
import static android.os.Looper.getMainLooper;
import static com.acemurder.datingme.R.id.my_page_edit_layout;
import static com.acemurder.datingme.R.id.tabMode;

/**
 * Created by fg on 2016/8/15.
 */
public class PersonalFragment extends Fragment {
    @BindView(my_page_edit_layout)
    LinearLayout myPageEditLayout;
    @BindView(R.id.my_page_relate_layout)
    RelativeLayout myPageRelateLayout;
    @BindView(R.id.my_page_trend_layout)
    RelativeLayout  myPageTrendLayout;
    @BindView(R.id.my_page_no_course_layout)
    RelativeLayout  myPageNoCourseLayout;
    @BindView(R.id.my_page_grade_layout)
    RelativeLayout  mContact;


    @BindView(R.id.my_page_avatar)
    CircleImageView myPageAvatar;
    @BindView(R.id.my_page_nick_name)
    TextView        myPageNickName;

    @BindView(R.id.my_page_introduce)
    TextView        myPageIntroduce;


    @OnClick(my_page_edit_layout)
    public void onEditClick(){
        Intent intent = new Intent(getActivity(),EditInfoActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.my_page_no_course_layout)
    public void onSettingClick(){
        startActivity(new Intent(getActivity(),SettingActivity.class));
    }


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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myPageNickName.setText(APP.getAVUser().getUsername());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();


    }

    private void initView() {
        RequestManager.INSTANCE.getUserInfo(new SimpleSubscriber<User>(getActivity(), new SubscriberListener<User>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext(User user) {
                super.onNext(user);
                APP.setUser(user);
            }

            @Override
            public void onStart() {
                super.onStart();
            }
        }),APP.getAVUser().getObjectId());
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfo();

    }

    private void setInfo() {
        if (APP.getUser().getPhotoSrc() != null && !APP.getUser().getPhotoSrc().equals("null"))
            Glide.with(getActivity()).load(APP.getUser().getPhotoSrc()).asBitmap().into(myPageAvatar);
        myPageNickName.setText(APP.getUser().getUsername());
        myPageIntroduce.setText(APP.getUser().getDescription());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoChangeEvent(UserInfoChangeEvent userInfoChangeEvent){
        Log.e("=========","mmmmmmmmmmmmmmmmm");
        initView();
        setInfo();
        //  mDatingItemList.clear();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}