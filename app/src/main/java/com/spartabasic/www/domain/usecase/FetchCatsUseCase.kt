package com.spartabasic.www.domain.usecase

import com.spartabasic.www.domain.repository.CatRepository

class FetchCatsUseCase
constructor(
    private val catsRepository: CatRepository
) {

    operator fun invoke(limit: Int, page: Int, tags: String?) =
        catsRepository.getCats(limit = limit, page = page, tags = tags)
}