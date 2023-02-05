package com.example.lastmarket;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"dc4e2ef727693d0a5ce1eed4e72be1dd");
    }
}
