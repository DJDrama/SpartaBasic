package com.spartabasic.www.data.network.source

import com.spartabasic.www.data.network.model.NetworkCat

interface CataasNetworkDataSource {

    suspend fun getCats(limit: Int, page: Int, tags: String? = null): List<NetworkCat>
}