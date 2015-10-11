package com.mcwilliams.TableTopicsApp.utils.network;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * Created by joshuamcwilliams on 10/11/15.
 */
public class ParseKeyRequestInterceptor implements Interceptor{
    private static final String TAG = ParseKeyRequestInterceptor.class.getSimpleName() ;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request compressedRequest = originalRequest.newBuilder()
                .addHeader("X-Parse-REST-API-Key", "Jse5QHuuyKHxcDrwQ6qBAMD2f3FjPBcwWclNjTDd")
                .addHeader("X-Parse-Application-Id","zAPsYqzpEh7oWXrlJl6nsHXD8eLVuQwZbRZllEOq")
                .build();

        Log.d(TAG, compressedRequest.urlString());
        Log.d(TAG, compressedRequest.headers().toString());
        return chain.proceed(compressedRequest);
    }
}
