package com.spartabasic.www.data.network.retrofit

import com.spartabasic.www.data.network.model.NetworkCat
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitCataasNetworkApi {
    @GET("api/cats")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int = 0,
        @Query("tags") tags: String?,
    ): List<NetworkCat>

}

interface Ktor {

}