package com.acemurder.datingme.modules.im.guide.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acemurder.datingme.R;
import com.acemurder.datingme.modules.im.guide.event.LeftChatItemClickEvent;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;


import java.text.SimpleDateFormat;


import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/8/13.
 * 聊天时居左的文本 holder
 */

public class LeftTextHolder extends AVCommonViewHolder {

  @BindView(R.id.chat_left_text_tv_time)
  protected TextView timeView;

  @BindView(R.id.chat_left_text_tv_content)
  protected TextView contentView;

  @BindView(R.id.chat_left_text_tv_name)
  protected TextView nameView;

  public LeftTextHolder(Context context, ViewGroup root) {
    super(context, root, R.layout.chat_left_text_view);
  }

  @OnClick({R.id.chat_left_text_tv_content, R.id.chat_left_text_tv_name})
  public void onNameClick(View view) {
    LeftChatItemClickEvent clickEvent = new LeftChatItemClickEvent();
    clickEvent.userId = nameView.getText().toString();
    EventBus.getDefault().post(clickEvent);
  }

  @Override
  public void bindData(Object o) {
    AVIMMessage message = (AVIMMessage)o;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    String time = dateFormat.format(message.getTimestamp());

    String content =  getContext().getString(R.string.unspport_message_type);
    if (message instanceof AVIMTextMessage) {
      content = ((AVIMTextMessage)message).getText();
    }

    contentView.setText(content);
    timeView.setText(time);
    nameView.setText(message.getFrom());
  }

  public void showTimeView(boolean isShow) {
    timeView.setVisibility(isShow ? View.VISIBLE : View.GONE);
  }
}