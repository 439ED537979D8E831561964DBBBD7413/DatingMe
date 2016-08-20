package com.acemurder.datingme.modules.dating;

import com.acemurder.datingme.data.bean.DatingItem;

/**
 * Created by fg on 2016/8/18.
 */
public class MessageEvent {
    public DatingItem mDatingItem;
    public MessageEvent(DatingItem datingItem){
        mDatingItem = datingItem;
    }
}
