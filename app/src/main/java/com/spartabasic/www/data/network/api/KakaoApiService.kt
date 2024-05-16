package com.spartabasic.www.data.network.api

import com.spartabasic.www.BuildConfig
import com.spartabasic.www.data.network.model.ImageSearchDto
import com.spartabasic.www.data.network.model.VideoSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {
    @GET("v2/search/image")
    suspend fun getSearchedImages(
        @Header("Authorization") header: String = "KakaoAK ${BuildConfig.REST_API_KEY}",
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<KakaoApiResponse<List<ImageSearchDto>>>

    @GET("v2/search/vclip")
    suspend fun getSearchedVideos(
        @Header("Authorization") header: String = "KakaoAK ${BuildConfig.REST_API_KEY}",
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<KakaoApiResponse<List<VideoSearchDto>>>
}