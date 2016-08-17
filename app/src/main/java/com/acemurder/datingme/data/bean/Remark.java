package com.acemurder.datingme.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/17.
 */

public class Remark {

    /**
     * authorName : null
     * authorId : null
     * communityId : cdskdscdskcndsds
     * content : 很好很好···
     * authorPhotoSrc : www.baidu.com
     * objectId : 57b4010c2e958a00562453f9
     * createdAt : 2016-08-17T06:15:40.994Z
     * updatedAt : 2016-08-17T06:16:07.636Z
     */

    private String authorName;
    private String authorId;
    private String communityId;
    private String content;
    private String authorPhotoSrc;
    private String objectId;
    private String createdAt;
    private String updatedAt;

    public static Remark objectFromData(String str) {

        return new Gson().fromJson(str, Remark.class);
    }

    public static Remark objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Remark.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Remark> arrayRemarkFromData(String str) {

        Type listType = new TypeToken<ArrayList<Remark>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Remark> arrayRemarkFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Remark>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorPhotoSrc() {
        return authorPhotoSrc;
    }

    public void setAuthorPhotoSrc(String authorPhotoSrc) {
        this.authorPhotoSrc = authorPhotoSrc;
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
}
