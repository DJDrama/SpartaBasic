package com.spartabasic.www.data.network.di

import com.spartabasic.www.BuildConfig
import com.spartabasic.www.data.network.retrofit.RetrofitCataasNetworkApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter()) // 날짜 형식 변환
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(
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

    @Singleton
    @Provides
    fun provideRetrofitModule(moshi: Moshi = provideMoshi()): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideCatsApiService(
        client: OkHttpClient = provideOkHttpClient(),
        retrofit: Retrofit.Builder = provideRetrofitModule()
    ): RetrofitCataasNetworkApi {
        return retrofit.baseUrl(BuildConfig.CATAAS_BASE_URL)
            .client(client)
            .build()
            .create(RetrofitCataasNetworkApi::class.java)
    }
}