package com.acemurder.datingme.data.network.interceptors;



import android.util.Log;

import com.acemurder.datingme.config.Const;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public class HeaderInterceptors implements Interceptor {


    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("X-LC-Id", Const.APP_ID)
                .addHeader("X-LC-Key", Const.APP_KEY);
        Log.e("HeaderInterceptors",original.url().toString());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
