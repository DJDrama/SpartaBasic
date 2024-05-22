package com.spartabasic.www.ui.model

import android.os.Parcelable
import com.spartabasic.www.BuildConfig
import com.spartabasic.www.domain.model.Cat
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatItem(
    val id: String,
    val tags: List<String>
) : Parcelable {
    val imageUrl: String
        get() = "${BuildConfig.CATAAS_BASE_URL}/cat/${this.id}"
}

fun Cat.toPresentationModel(): CatItem {
    return CatItem(id = id, tags = tags)
}