package com.acemurder.datingme.network.service;

import com.acemurder.datingme.bean.DatingItem;
import com.acemurder.datingme.bean.ResultWrapper;
import com.acemurder.datingme.config.Api;
import com.acemurder.datingme.config.Const;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public interface LeanCloudApiService {

    @GET(Api.API_GET_DATING_ITEM)
    Observable<ResultWrapper<List<DatingItem>>> getDatingItems(@Query("limit")String page, @Query("skip")String size);



}
