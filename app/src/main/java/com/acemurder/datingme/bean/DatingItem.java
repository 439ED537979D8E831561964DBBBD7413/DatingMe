package com.acemurder.datingme.bean;


import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import static android.icu.util.HebrewCalendar.AV;

/**
 * Created by  : ACEMURDER
 * Created at  : 16/8/14.
 * Created for : DatingMe
 */
public class DatingItem{




    /**
     * hasDated : false
     * promulgator :
     * content : null
     * theme : 篮球
     * receiver : null
     * objectId : 57afd80a7db2a200542048db
     * createdAt : 2016-08-14T02:31:38.613Z
     * updatedAt : 2016-08-14T02:31:38.613Z
     */

    private boolean hasDated;
    private String promulgator;
    private String content;
    private String theme;
    private String receiver;
    private String objectId;
    private String createdAt;
    private String updatedAt;
    private String promulgatorId;
    private String receiverId;
    private String photoSrc;

    public String getPhotoSrc() {
        return photoSrc;
    }

    public void setPhotoSrc(String photoSrc) {
        this.photoSrc = photoSrc;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isHasDated() {
        return hasDated;
    }

    public String getPromulgatorId() {
        return promulgatorId;
    }

    public void setPromulgatorId(String promulgatorId) {
        this.promulgatorId = promulgatorId;
    }

    public boolean hasDated() {
        return hasDated;
    }

    public void setHasDated(boolean hasDated) {
        this.hasDated = hasDated;
    }

    public String getPromulgator() {
        return promulgator;
    }

    public void setPromulgator(String promulgator) {
        this.promulgator = promulgator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
