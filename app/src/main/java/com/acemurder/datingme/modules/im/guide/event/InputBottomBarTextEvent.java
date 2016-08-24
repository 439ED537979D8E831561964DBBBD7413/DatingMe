package com.acemurder.datingme.modules.im.guide.event;

/**
 * Created by zhengyuxuan on 16/8/24.
 */
public class InputBottomBarTextEvent extends InputBottomBarEvent {

  /**
   * 发送的文本内容
   */
  public String sendContent;

  public InputBottomBarTextEvent(int action, String content, Object tag) {
    super(action, tag);
    sendContent = content;
  }
}
