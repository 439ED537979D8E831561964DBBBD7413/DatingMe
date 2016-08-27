package com.acemurder.datingme.data.network.service;

import android.support.design.widget.Snackbar;

import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.data.bean.Community;
import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.Remark;
import com.acemurder.datingme.data.bean.Response;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.data.bean.User;
import com.avos.avoscloud.AVUser;

import java.util.List;
import java.util.SplittableRandom;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by zhengyuxuan on 16/8/15.
 */

public interface LeanCloudApiService {


    @POST(Api.API_LOGIN)
    Observable<User>login(@Field("username")String userName, @Field("password")String password);

    @GET(Api.API_GET_DATING_ITEM)
    Observable<ResultWrapper<List<DatingItem>>> getDatingItems(@Query("limit")String size,
                                                               @Query("skip") String page,
                                                               @Query("order") String order);

    @GET(Api.API_GET_COMMUNITY)
    Observable<ResultWrapper<List<Community>>> getCommunityItems(@Query("limit")String size,
                                                                 @Query("skip")String page,
                                                                 @Query("order") String order);

    @GET(Api.API_GET_ALL_USER)
    Observable<ResultWrapper<List<User>>> getAlluser();

    @PUT(Api.API_GET_DATING_ITEM+"/"+"{PATH}")
    Observable<Response>date(@Path("PATH")String id,@Body RequestBody body);

    @POST(Api.API_SIGN_UP)
    Observable<AVUser> signIn(@Body String data);

    @POST(Api.API_GET_DATING_ITEM)
    Observable<Response>addDatingItem(@Body RequestBody data);

    @POST(Api.API_GET_COMMUNITY)
    Observable<Response>addCommunityItem(@Body RequestBody data);

    @GET(Api.API_GET_Remark_ITEM)
    Observable<ResultWrapper<List<Remark>>>getRemarkItems(@Query("where")String data,@Query("order") String order);

    @POST(Api.API_GET_Remark_ITEM)
    Observable<Response>addRemarkItem(@Body RequestBody data,@Query("order")String order);

    @GET(Api.API_GET_DATING_ITEM)
    Observable<ResultWrapper<List<DatingItem>>>getMyDatingItem(@Query("where")String where,@Query("order") String order);



}




