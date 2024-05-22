package com.spartabasic.www.domain.repository

import com.spartabasic.www.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    fun getCats(limit: Int, page: Int, tags: String?): Flow<List<Cat>>
}