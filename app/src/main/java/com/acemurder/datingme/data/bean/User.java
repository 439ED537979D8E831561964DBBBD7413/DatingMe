package com.acemurder.datingme.data.bean;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by zhengyuxuan on 16/8/19.
 */

public class User  {

    /**
     * description : 这个人很懒，什么都没有留下！
     * sessionToken : ykd3f7dunv57sj8x1nz579gqd
     * updatedAt : 2016-08-08T05:04:49.351Z
     * objectId : 57a812f1a341310063428249
     * photoSrc : null
     * username : acemurder
     * createdAt : 2016-08-08T05:04:49.351Z
     * emailVerified : false
     * mobilePhoneVerified : false
     */

    private String description;
    private String sessionToken;
    private String updatedAt;
    private String objectId;
    private String photoSrc;
    private String username;
    private String createdAt;
    private boolean emailVerified;
    private boolean mobilePhoneVerified;



    public static User objectFromData(String str) {

        return new Gson().fromJson(str, User.class);
    }

    public static User objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), User.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<User> arrayUserFromData(String str) {

        Type listType = new TypeToken<ArrayList<User>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<User> arrayUserFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }
}
