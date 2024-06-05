package com.spartabasic.www.data.repository

import com.spartabasic.www.data.network.model.NetworkCat
import com.spartabasic.www.data.network.model.asExternalModel
import com.spartabasic.www.data.network.source.CataasNetworkDataSource
import com.spartabasic.www.domain.model.Cat
import com.spartabasic.www.domain.repository.CatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CataasRepository
@Inject
constructor(
    private val cataasNetworkDataSource: CataasNetworkDataSource,
) : CatRepository {


    override fun getCats(limit: Int, page: Int, tags: String?): Flow<List<Cat>> {
        return flow {
           // while(true){
                val result = cataasNetworkDataSource.getCats(limit = limit, page = page, tags = tags)
                    .map(NetworkCat::asExternalModel)
                emit(value = result)
              //  delay(5000)
           // }
        }
    }
}