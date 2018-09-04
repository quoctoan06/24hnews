package com.example.toan.interfaces;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Toan on 11-Feb-18.
 */

public interface HttpCallback {
    void onSuccess(String s);

    void onStart();

    void onFailure(Request request, IOException e);
}
