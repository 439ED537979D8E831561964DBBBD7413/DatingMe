package com.acemurder.datingme.data.network.service;

import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.config.Api;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.okhttp.Request;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static android.R.attr.data;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public interface LeanCloudApiService {

    @GET(Api.API_GET_DATING_ITEM)
    Observable<ResultWrapper<List<DatingItem>>> getDatingItems(@Query("limit")String size, @Query("skip")String page);

    @POST(Api.API_SIGN_UP)
    Observable<AVUser> signIn(@Body()String data);

    @POST(Api.API_GET_DATING_ITEM)
    Observable<Response>addItem(@Body RequestBody data);
}




