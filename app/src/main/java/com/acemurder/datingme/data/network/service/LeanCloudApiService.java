package com.acemurder.datingme.data.network.service;

import com.acemurder.datingme.data.bean.DatingItem;
import com.acemurder.datingme.data.bean.ResultWrapper;
import com.acemurder.datingme.config.Api;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public interface LeanCloudApiService {

    @GET(Api.API_GET_DATING_ITEM)
    Observable<ResultWrapper<List<DatingItem>>> getDatingItems(@Query("limit")String page, @Query("skip")String size);



}
