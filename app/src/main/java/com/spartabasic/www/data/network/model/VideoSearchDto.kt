package com.spartabasic.www.data.network.model

import com.squareup.moshi.Json
import java.util.Date

data class VideoSearchDto(
    val title: String,
    val url: String,
    @Json(name = "datetime")
    val dateTime: Date,
    @Json(name = "play_time")
    val playTime: Int,
    val thumbnail: String,
    val author: String
)
