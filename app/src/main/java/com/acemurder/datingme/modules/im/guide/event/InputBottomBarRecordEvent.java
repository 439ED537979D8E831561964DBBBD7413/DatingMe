package com.acemurder.datingme.modules.im.guide.event;

/**
 * Created by zhengyuxuan on 16/8/24.
 */
public class InputBottomBarRecordEvent extends InputBottomBarEvent {

  /**
   * 录音本地路径
   */
  public String audioPath;

  /**
   * 录音长度
   */
  public int audioDuration;

  public InputBottomBarRecordEvent(int action, String path, int duration, Object tag) {
    super(action, tag);
    audioDuration = duration;
    audioPath = path;
  }
}
