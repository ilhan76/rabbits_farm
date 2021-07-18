package com.kudashov.rabbits_farm.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        val baseUrl: String = "https://rabbit-api--test.herokuapp.com/"
        var retrofit: Retrofit? = null

        val client: Retrofit
            get() {
                if (retrofit == null) {
                    val httpClient = OkHttpClient.Builder()
                    //logging interceptor
                    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

                    //httpClient.addInterceptor(loggingInterceptor)
                    httpClient.connectTimeout(20, TimeUnit.SECONDS)

                    val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .client(httpClient.build())

                    retrofit = retrofitBuilder.build()
                }
                return retrofit!!
            }
    }
}