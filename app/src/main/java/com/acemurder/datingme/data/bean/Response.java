package com.acemurder.datingme.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengyuxuan on 16/8/16.
 */

public class Response {

    /**
     * createdAt : 2015-06-29T01:39:35.931Z
     * objectId : 558e20cbe4b060308e3eb36c
     */

    private String createdAt;
    private String objectId;

    public static Response objectFromData(String str) {

        return new Gson().fromJson(str, Response.class);
    }

    public static Response objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Response.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Response> arrayResponseFromData(String str) {

        Type listType = new TypeToken<ArrayList<Response>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Response> arrayResponseFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Response>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Response{" +
                "createdAt='" + createdAt + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
