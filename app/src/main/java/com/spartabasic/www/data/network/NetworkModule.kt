package com.spartabasic.www.data.network

import com.spartabasic.www.BuildConfig
import com.spartabasic.www.data.network.api.KakaoApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

object NetworkModule {

    val retrofit by lazy {
        provideKakaoApiService()
    }

    private fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter()) // 날짜 형식 변환
            .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    private fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor = provideHttpLoggingInterceptor(),
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(timeout = 20, unit = TimeUnit.SECONDS)
            .readTimeout(timeout = 20, unit = TimeUnit.SECONDS)
            .writeTimeout(timeout = 20, unit = TimeUnit.SECONDS)
            .addInterceptor(interceptor = httpLoggingInterceptor)
            .build()
    }

    private fun provideRetrofitModule(moshi: Moshi = provideMoshi()): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    private fun provideKakaoApiService(
        client: OkHttpClient = provideOkHttpClient(),
        retrofit: Retrofit.Builder = provideRetrofitModule()
    ): KakaoApiService {
        return retrofit.baseUrl(BuildConfig.KAKAO_BASE_URL)
            .client(client)
            .build()
            .create(KakaoApiService::class.java)
    }
}