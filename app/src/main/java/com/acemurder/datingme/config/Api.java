package com.acemurder.datingme.config;

import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.util.SPUtils;

import java.security.PublicKey;

import rx.Subscription;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public class Api {
    public static final String ALIYUN_END_POINT ="http://image.acemurder.com/";
    public static final String BASE_URL = "https://api.leancloud.cn/";
    public static final String API_GET_DATING_ITEM ="1.1/classes/DatingItem";
    public static final String API_SIGN_UP = "https://api.leancloud.cn/1.1/users";
    public static final String API_GET_COMMUNITY = "1.1/classes/Community";
    public static final String API_GET_Remark_ITEM ="1.1/classes/Remark";
    public static final String API_LOGIN = "1.1/login";
    public static final String API_GET_ALL_USER = "1.1/classes/_user";
    public static final String API_PUT_USER = "1.1/users";
}
