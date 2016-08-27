package com.acemurder.datingme.config;


import com.acemurder.datingme.util.SPUtils;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;

/**
 * Created by zhengyuxuan on 16/8/8.
 */

public class Const {

    public static final String endpoint = "image.acemurder.com";
    public static final String accessKeyId = "0rGiins1DWdpWOuc";
    public static final String accessKeySecret = "bVm4Fy0IyFCulScJWdHtO0Vy9H6HAT";


    //public static final String APP_ID = "vnRdeLMsUKJJFBOK4u4MtqBv-gzGzoHsz";
    //public static final String APP_KEY = "9qxFgPgPvDiKfTdjloLJbJG5";

    public static final String APP_ID = "lDpwsLMUytrYKTpcfhWfXt6S-gzGzoHsz";
    public static final String APP_KEY = "oq27wJHTd84eAoFfq4BhuW4C";

    public static final String DATING_ITEM = "DatingItem";

    public static final String SP_USER_NAME = "user_name";
    public static final String SP_USER_PASS = "user_pass";

    public static final String SP_USER_OBJECT_ID = "user_id";
    public static final String SP_USER_PHOTO_SRC = "user_photo";
    public static final String SP_KEY_USER = "dating_me_user";


    public static final int UNKNOWN_WRONG = -1;
    public static final int HAS_SAME_NAME = 202;
    public static final int PASSWORD_WRONG = 210;
    public static final int USER_NOT_EXIST = 211;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    public static final class Requests {
        public static final int SELECT_PICTURE = 1;
        public static final int SELECT_CAMERA = 2;

    }

}
