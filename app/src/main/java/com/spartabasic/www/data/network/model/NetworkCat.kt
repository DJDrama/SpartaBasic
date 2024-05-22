package com.spartabasic.www.data.network.model

import com.spartabasic.www.domain.model.Cat
import com.squareup.moshi.Json

data class NetworkCat(
    @Json(name = "_id")
    val id: String,
    @Json(name = "mimetype")
    val mimeType: String,
    val size: Int,
    val tags: List<String>
)

fun NetworkCat.asExternalModel() = Cat(
    id = this.id,
    tags = this.tags
)