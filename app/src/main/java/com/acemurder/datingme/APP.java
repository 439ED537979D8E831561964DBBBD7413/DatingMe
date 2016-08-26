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

import static com.acemurder.datingme.config.Const.APP_KEY;

import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by zhengyuxuan on 16/8/4.
 */

public class APP extends Application {

    private static AVUser mAVUser;
    private static Context sContext;
    private static boolean  hasLogined = false;


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

     //   AVIMMessageManager.registerDefaultMessageHandler(new MessageHandler());


        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

    }


    public static void setUser(AVUser user) {
        if (user != null){
            mAVUser = user;
            SPUtils.set(getContext(), Const.SP_USER_NAME, user.getUsername());
            SPUtils.set(getContext(), Const.SP_USER_NAME, user.getObjectId());
            hasLogined = true;
        }else
            hasLogined = false;

    }

    public static boolean hasLogin(){
        return hasLogined;
    }

    public static AVUser getAVUser() {
        return mAVUser;
    }

    public static Context getContext() {
        return sContext;
    }
}
