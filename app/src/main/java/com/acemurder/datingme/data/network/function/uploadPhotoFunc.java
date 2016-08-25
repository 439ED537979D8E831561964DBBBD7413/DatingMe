package com.acemurder.datingme.data.network.function;

import android.util.Log;

import com.acemurder.datingme.data.bean.Response;
import com.alibaba.fastjson.parser.deserializer.IntegerFieldDeserializer;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import retrofit2.http.Field;
import rx.functions.Func1;


/**
 * Created by zhengyuxuan on 16/8/19.
 */

public class uploadPhotoFunc implements Func1<PutObjectResult,Response>{


    @Override
    public Response call(PutObjectResult putObjectResult) {
        Log.e("==========",putObjectResult.getETag());
        Log.e("=========",putObjectResult.getStatusCode()+"");
        Log.e("==========",putObjectResult.getServerCallbackReturnBody());
        return null;
    }
}
