package com.acemurder.datingme;

import android.app.Application;
import android.content.Context;

import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.data.bean.User;
import com.acemurder.datingme.modules.im.guide.MessageHandler;
import com.acemurder.datingme.util.SPUtils;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

import org.greenrobot.eventbus.Subscribe;

import static android.icu.util.HebrewCalendar.AV;
import static com.acemurder.datingme.util.SPUtils.get;

/**
 * Created by zhengyuxuan on 16/8/4.
 */

public class APP extends Application {

    private static AVUser mAVUser;

    public static User getUser() {
        return sUser;
    }

    public static void setUser(User user) {
        sUser = user;
    }

    private static User sUser;
    private static Context sContext;
    private static String token;

    public static void setHasLogined(boolean hasLogined) {
        APP.hasLogined = hasLogined;
    }

    private static boolean  hasLogined = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        AVOSCloud.initialize(this, Const.APP_ID, Const.APP_KEY);
        AVOSCloud.useAVCloudCN();

        // 必须在启动的时候注册 MessageHandler
        // 应用一启动就会重连，服务器会推送离线消息过来，需要 MessageHandler 来处理
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new MessageHandler(this));

    }


    public static void setUser(AVUser user) {
        if (user != null){
            mAVUser = user;
            sUser = new User();
            sUser.setObjectId(user.getObjectId());
            sUser.setUsername(user.getUsername());
            sUser.setDescription("这个人好懒,什么都没有留下");
            sUser.setPhotoSrc("null");
            token = mAVUser.getSessionToken();
            SPUtils.set(getContext(), Const.SP_USER_NAME, user.getUsername());
            SPUtils.set(getContext(), Const.SP_USER_OBJECT_ID, user.getObjectId());
            hasLogined = true;
        }else
            hasLogined = false;

    }

    public static boolean hasLogin(){
        if (!hasLogined){
            String name = (String) SPUtils.get(getContext(), Const.SP_USER_NAME, "");
            String id = (String) SPUtils.get(getContext(), Const.SP_USER_OBJECT_ID, "");
            if (!name.equals("") && !id.equals("")){
                mAVUser = new AVUser();
                mAVUser.setUsername(name);
                mAVUser.setObjectId(id);
                sUser = new User();
                sUser.setObjectId(id);
                sUser.setUsername(name);
                sUser.setDescription("这个人好懒,什么都没有留下");
                return true;
            }
        }
        return hasLogined;
    }

    public static AVUser getAVUser() {
        return mAVUser;
    }

    public static Context getContext() {
        return sContext;
    }
}