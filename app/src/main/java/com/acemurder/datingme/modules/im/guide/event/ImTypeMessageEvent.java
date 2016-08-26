package com.acemurder.datingme.modules.im.guide.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by zhengyuxuan on 16/8/24.
 */
public class ImTypeMessageEvent {
  public AVIMTypedMessage message;
  public AVIMConversation conversation;
}
