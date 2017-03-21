package com.example.administrator.moviesallyear;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/20.
 */

public class QuanysRetrofit {

    final DoubanApi doubanService;
    final GankApi gankService;


    // @formatter:off
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    // @formatter:on


    QuanysRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (QuanysFactory.isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        httpClient.connectTimeout(12, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit.Builder builder1 = new Retrofit.Builder();

        builder.baseUrl("https://api.douban.com/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        builder1.baseUrl("http://gank.io/api/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit doubanRest = builder.build();
        Retrofit gankRest = builder1.build();
        doubanService = doubanRest.create(DoubanApi.class);
        gankService = gankRest.create(GankApi.class);
    }


    public DoubanApi getdoubanService() {
        return doubanService;
    }

    public GankApi getGankService() {
        return gankService;
    }
}

