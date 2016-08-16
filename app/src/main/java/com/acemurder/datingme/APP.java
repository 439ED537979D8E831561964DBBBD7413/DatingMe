package com.acemurder.datingme;

import android.app.Application;
import android.content.Context;

import com.acemurder.datingme.config.Const;
import com.acemurder.datingme.util.SPUtils;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;

/**
 * Created by zhengyuxuan on 16/8/4.
 */

public class APP extends Application {

    private static AVUser mAVUser;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        AVOSCloud.initialize(this, Const.APP_ID, Const.APP_KEY);
        AVOSCloud.useAVCloudCN();
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
