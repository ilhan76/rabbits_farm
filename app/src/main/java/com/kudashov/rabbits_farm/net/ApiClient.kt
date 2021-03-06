package com.kudashov.rabbits_farm.net

import com.kudashov.rabbits_farm.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        private const val BASE_URL: String = "https://rabbit-api--app.herokuapp.com/"
      
        var retrofit: Retrofit? = null

        val client: Retrofit
            get() {
                if (retrofit == null) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

                    val httpClient = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .retryOnConnectionFailure(true)

                    httpClient.connectTimeout(20000, TimeUnit.SECONDS)

                    val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .client(httpClient.build())

                    retrofit = retrofitBuilder.build()
                }
                return retrofit!!
            }
    }
}
