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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fg on 2016/8/25.
 */
public class ConfigureFragment extends Fragment implements PersonalContract.IConfigureView{

    @BindView(R.id.fragment_configure_portrait)ImageView portraitView;
    @BindView(R.id.fragment_configure_nickName)EditText nickNameView;
    @BindView(R.id.fragment_configure_sex)TextView sexView;
    @BindView(R.id.fragment_configure_signature)EditText signatureView;
    @BindView(R.id.fragment_configure_button)Button button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configure,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void showPersonalDetails() {

    }

    @Override
    public void showGetPersonalDetailsError() {

    }

    @Override
    public void showSendError() {

    }

    @Override
    public void showSendSuccess() {

    }


    @Override
    public void setPresenter(PersonalContract.IConfigurePresenter presenter) {

    }
}
