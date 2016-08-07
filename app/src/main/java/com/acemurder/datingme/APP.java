package com.acemurder.datingme;

import android.app.Application;

import com.acemurder.datingme.config.Const;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;

/**
 * Created by zhengyuxuan on 16/8/4.
 */

public class APP extends Application {

    private static AVUser mAVUser;


    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, Const.APP_ID,Const.APP_KEY);
    }


    public static void setUser(AVUser user){
        mAVUser = user;
    }

    public static AVUser getAVUser(){
        return mAVUser;
    }
}
