package com.example.lastmarket.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitHelper {

    public static String baseUrl="http://jeilpharm.dothome.co.kr/";
    public static Gson gson= new GsonBuilder().setLenient().create();

    public static Retrofit getRetrofitInstanceGson(){
        Retrofit.Builder builder=new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();

        return retrofit;

    }

    public static  Retrofit getRetrofitInstanceScalars(){
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit=builder.build();

        return retrofit;
    }
}
