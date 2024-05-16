package com.spartabasic.www.data.network.api

import com.spartabasic.www.data.network.model.MetaDto

data class KakaoApiResponse<out T>(
    val meta: MetaDto,
    val documents: T,
)

