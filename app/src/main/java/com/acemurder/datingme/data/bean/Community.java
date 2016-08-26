package com.acemurder.datingme.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/17.
 */

public class Community implements Cloneable ,Serializable{


    /**
     * authorName : acemurder
     * authorId : null
     * content : null
     * title : null
     * photoSrc : ["www.acemurder","baidu.com"]
     * objectId : 57b3fb4a8d2a3b0069607ccc
     * createdAt : 2016-08-17T05:51:06.025Z
     * updatedAt : 2016-08-17T05:54:14.006Z
     */

    private String authorName;
    private String authorId;
    private String content;
    private String title;
    private String objectId;
    private String createdAt;
    private String updatedAt;
    private String authorPhoto;

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public void setAuthorPhoto(String authorPhoto) {
        this.authorPhoto = authorPhoto;
    }

    private List<String> photoSrc = new ArrayList<>();

    public static Community objectFromData(String str) {

        return new Gson().fromJson(str, Community.class);
    }

    public static Community objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Community.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Community> arrayCommunityFromData(String str) {

        Type listType = new TypeToken<ArrayList<Community>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Community> arrayCommunityFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Community>>() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(List<String> photoSrc) {
        this.photoSrc = photoSrc;
    }

    @Override
    public String toString() {
        StringBuffer photos = new StringBuffer();
        photos.append("[");

        if (photoSrc.size() != 0){
            for (int i = 0 ; i < photoSrc.size() -1 ; i++){
                photos.append("\"");
                photos.append(photoSrc.get(i));
                photos.append("\",");

            }
            photos.append("\""+photoSrc.get(photoSrc.size() - 1) + "\"]");
        }else
            photos.append("]");


        return  "{" +
                "\"" + "authorName" + "\"" + ":" + "\"" + authorName + "\","+
                "\"" + "authorId" + "\"" + ":" + "\"" + authorId + "\","+
                "\"" + "content" + "\"" + ":" + "\"" + content + "\","+
                "\"" + "title" + "\"" + ":" + "\"" + title + "\","+
                "\"" + "photoSrc" + "\"" + ":" +  photos.toString() + "}";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



}
