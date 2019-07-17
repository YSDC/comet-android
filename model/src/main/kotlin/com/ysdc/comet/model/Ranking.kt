package com.ysdc.comet.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class Ranking(
    @field:Json(name = "isLargeField")
    val isLargeField: Boolean,
    @field:Json(name = "rankEntries")
    val rankEntries: List<RankEntry>
) : Parcelable