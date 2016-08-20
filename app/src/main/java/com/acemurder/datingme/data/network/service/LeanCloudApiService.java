package com.acemurder.datingme.data.network.service;

import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.avos.avoscloud.AVUser;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public interface LeanCloudApiService {

    @GET(Api.API_GET_DATING_ITEM)
    Observable<ResultWrapper<List<DatingItem>>> getDatingItems(@Query("limit")String size,
                                                               @Query("skip") String page,
                                                               @Query("order") String order);

    @GET(Api.API_GET_COMMUNITY)
    Observable<ResultWrapper<List<Community>>> getCommunityItems(@Query("limit")String size,
                                                                 @Query("skip")String page,
                                                                 @Query("order") String order);

    @POST(Api.API_SIGN_UP)
    Observable<AVUser> signIn(@Body()String data);

    @POST(Api.API_GET_DATING_ITEM)
    Observable<Response>addDatingItem(@Body RequestBody data);

    @POST(Api.API_GET_COMMUNITY)
    Observable<Response>addCommunityItem(@Body RequestBody data);

    @GET(Api.API_GET_Remark_ITEM)
    Observable<ResultWrapper<List<Remark>>>getRemarkItems(@Query("where")String data,@Query("order") String order);

    @POST(Api.API_GET_Remark_ITEM)
    Observable<Response>addRemarkItem(@Body RequestBody data);

}




