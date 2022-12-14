package com.example.tbc_final.domain.model

import android.os.Parcelable
import com.example.tbc_final.presentation.base.BaseDiff
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sneakers(
    val id: Int?,
    val storyHtml:String?,
    val retailPriceCents:Int?,
    val name: String?,
    val mainPictureUrl: String?,
    var isFavorite: Boolean = false,

): BaseDiff<Sneakers>(),Parcelable{

    override val inner: Sneakers
        get() = this

    override val uniqueValue: Any
        get() = id ?: ""

    override fun compareTo(other: Any?): Boolean {
        return other is Sneakers && this == other
    }
    fun toSneakerModelSneaker()=
        SneakerModel.Sneaker(
            id = id,
            name = name,
            mainPictureUrl = mainPictureUrl,
            retailPriceCents = retailPriceCents,
            storyHtml = storyHtml,
            isFavorite = isFavorite,
            category = null
        )
}


