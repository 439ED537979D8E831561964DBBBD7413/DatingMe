package com.acemurder.datingme.network.interceptors;

import com.acemurder.datingme.config.Const;
import com.avos.avoscloud.okhttp.Interceptor;
import com.avos.avoscloud.okhttp.Request;
import com.avos.avoscloud.okhttp.Response;

import java.io.IOException;

/**
 * Created by zhengyuxuan on 16/8/15.
 */

public class HeaderInterceptors implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("X-LC-Id", Const.APP_ID)
                .addHeader("X-LC-Key", Const.APP_KEY);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
