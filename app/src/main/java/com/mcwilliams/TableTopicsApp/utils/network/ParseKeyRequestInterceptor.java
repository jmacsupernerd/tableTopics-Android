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
                .addHeader("application-id",  "E66CF4B4-7B10-5D36-FFEB-C9F07111E500")
                .addHeader("secret-key", "A1661544-B0C9-9B5B-FF60-3BDC1BD32100")
                .build();

        Log.d(TAG, compressedRequest.urlString());
        Log.d(TAG, compressedRequest.headers().toString());
        return chain.proceed(compressedRequest);
    }
}
