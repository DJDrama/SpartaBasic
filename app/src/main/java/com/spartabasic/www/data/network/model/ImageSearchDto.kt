package com.spartabasic.www.data.network.model

import com.squareup.moshi.Json
import java.util.Date

data class ImageSearchDto(
    val collection: String,
    @Json(name = "thumbnail_url")
    val thumbnailUrl: String,
    @Json(name = "image_url")
    val imageUrl: String,
    val width: Int,
    val height: Int,
    @Json(name = "display_sitename")
    val displaySiteName: String,
    @Json(name = "doc_url")
    val docUrl: String,
    @Json(name = "datetime")
    val dateTime: Date
)
