package com.acemurder.datingme.modules.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.modules.im.guide.Constants;
import com.acemurder.datingme.modules.im.guide.activity.AVSquareActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengyuxuan on 15/12/4.
 * 联系人页面
 */
public class ContactFragment extends Fragment {

  @BindView(R.id.chatting_tv_go_square)
  TextView mGoText;


  @OnClick(R.id.chatting_tv_go_square)
  public void OnClick(){
    Intent intent = new Intent(getActivity(), AVSquareActivity.class);
    intent.putExtra(Constants.CONVERSATION_ID, Constants.SQUARE_CONVERSATION_ID);
    intent.putExtra(Constants.ACTIVITY_TITLE, getString(R.string.square_name));
    startActivity(intent);

  }



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.contact_fragment, container, false);
    ButterKnife.bind(this,view);
    return view;
  }

  @Override
  public void onDestroyView() {
    EventBus.getDefault().unregister(this);
    super.onDestroyView();
  }

  @Override
  public void onResume() {
    super.onResume();
    refreshMembers();
  }

  private void refreshMembers() {

  }



}
