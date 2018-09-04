package com.example.toan.api;

import android.content.Context;

import com.example.toan.api.base.BaseOkHttp;
import com.example.toan.interfaces.HttpCallback;
import com.example.toan.util.Define;
import com.example.toan.util.LogUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Toan on 11-Feb-18.
 */

public class NewsApi {

    public static void getListPost(Context ctx, int categoryId, int limit, int offset, HttpCallback httpCallback) {
        BaseOkHttp baseOkHttp = new BaseOkHttp.Builder()                    //B1: Tạo Callback
                .setHttpCallback(httpCallback)
                .setContext(ctx).setWantShowDialog(true)
                .setWantDialogCancelable(true).setTitle("....").setMessage("Loading...").build();

        OkHttpClient okHttpClient = BaseOkHttp.getOkHttpClient();      //B2: Tạo OkHttp

        // ?category_id=2&limit=10&offset=0
        String url = Define.API_GET_LIST_POST + "?category_id=" + categoryId + "&limit=" + limit + "&offset=" + offset;

        LogUtil.d("getListPost", url);

        Request request = new Request.Builder().url(url).build();    // Tạo request đến một API nào đó

        okHttpClient.newCall(request).enqueue(baseOkHttp);  // Execute request
    }

    public static void getPostDetail(Context ctx, int post_id, HttpCallback httpCallback) {
        BaseOkHttp baseOkHttp = new BaseOkHttp.Builder()                    //B1: Tạo Callback
                .setHttpCallback(httpCallback)
                .setContext(ctx).setWantShowDialog(true)
                .setWantDialogCancelable(true).setTitle("....").setMessage("Loading...").build();

        OkHttpClient okHttpClient = BaseOkHttp.getOkHttpClient();      //B2: Tạo OkHttp

        // ?post_id=82
        String url = Define.API_GET_POST_DETAIL + "?post_id=" + post_id;

        LogUtil.d("getPostDetail", url);

        Request request = new Request.Builder().url(url).build();    // Tạo request đến một API nào đó

        okHttpClient.newCall(request).enqueue(baseOkHttp);  // Execute request
    }
}
