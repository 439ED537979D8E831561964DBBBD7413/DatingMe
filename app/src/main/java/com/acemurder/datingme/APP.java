package com.acemurder.datingme;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.modules.im.CustomUserProvider;
import com.acemurder.datingme.util.SPUtils;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import cn.leancloud.chatkit.LCChatKit;

import static com.acemurder.datingme.config.Const.APP_KEY;

/**
 * Created by zhengyuxuan on 16/8/4.
 */

public class APP extends Application {

    private static AVUser mAVUser;
    private static Context sContext;


    public static class MessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Log.d("Tom & Jerry",((AVIMTextMessage)message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client){

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        AVOSCloud.initialize(this, Const.APP_ID, APP_KEY);
        AVOSCloud.useAVCloudCN();
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), Const.APP_ID, Const.APP_KEY);
     //   AVIMMessageManager.registerDefaultMessageHandler(new MessageHandler());


    }


    public static void setUser(AVUser user) {
        mAVUser = user;
        SPUtils.set(getContext(), Const.SP_USER_NAME, user.getUsername());
        SPUtils.set(getContext(), Const.SP_USER_NAME, user.getObjectId());
    }

    public static AVUser getAVUser() {
        return mAVUser;
    }

    public static Context getContext() {
        return sContext;
    }
}
