package com.example.administrator.myapplication.Tools;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by youwenzhi on 2018/4/13.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
