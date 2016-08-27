package com.acemurder.datingme.data.bean;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhengyuxuan on 16/8/19.
 */

public class User implements Serializable{


    /**
     * description : 这个人很懒，什么都没有留下！
     * photoSrc : http://image.acemurder.com/DatingMe/moiling.jpg
     * username : HW7yqh  A OZqy.,t  Bx s. Rn
     * emailVerified : false
     * mobilePhoneVerified : false
     * objectId : 57bf85776be3ff005820808b
     * createdAt : 2016-08-25T23:55:35.198Z
     * updatedAt : 2016-08-25T23:55:35.198Z
     */

    private String description;
    private String photoSrc;
    private String username;
    private boolean emailVerified;
    private boolean mobilePhoneVerified;
    private String objectId;
    private String createdAt;
    private String updatedAt;

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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {

        String data = "{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":" + "\""+objectId + "\""+ "}";
        return data;
    }
}