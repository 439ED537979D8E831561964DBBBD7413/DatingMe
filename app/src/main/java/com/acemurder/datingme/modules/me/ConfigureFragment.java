package com.acemurder.datingme.modules.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.data.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fg on 2016/8/25.
 */
public class ConfigureFragment extends Fragment implements PersonalContract.IConfigureView{
    private User mUser;
    private Unbinder mUnbinder;

    @BindView(R.id.fragment_configure_portrait)ImageView portraitView;
    @BindView(R.id.fragment_configure_nickName)EditText nickNameView;
    @BindView(R.id.fragment_configure_sex)TextView sexView;
    @BindView(R.id.fragment_configure_signature)EditText signatureView;
    @BindView(R.id.fragment_configure_button)Button button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configure,container,false);
        mUnbinder = ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void showSendSuccess() {

    }

    @Override
    public void showSendError() {

    }
    @Override
    public void setPresenter(PersonalContract.IConfigurePresenter presenter) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
