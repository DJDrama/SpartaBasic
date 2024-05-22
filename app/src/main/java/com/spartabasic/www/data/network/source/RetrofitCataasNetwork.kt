package com.spartabasic.www.data.network.source

import com.spartabasic.www.data.network.model.NetworkCat
import com.spartabasic.www.data.network.retrofit.RetrofitCataasNetworkApi
import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitCataasNetwork
@Inject
constructor(
    private val retrofitCataasNetworkApi: RetrofitCataasNetworkApi
) : CataasNetworkDataSource {
    override suspend fun getCats(limit: Int, page: Int, tags: String?): List<NetworkCat> {
        return retrofitCataasNetworkApi.getCats(limit = limit, skip = page, tags = tags)
    }
}