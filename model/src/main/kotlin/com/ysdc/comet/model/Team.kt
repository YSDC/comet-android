package com.ysdc.comet.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Team (
    @field:Json(name = "id")
    val id : Int,
    @field:Json(name = "name")
    val name : String,
    @field:Json(name = "league")
    var league : Int? = null,
    @field:Json(name = "gameClass")
    var gameClass : Int? = null,
    @field:Json(name = "group")
    var group : String? = null
): Parcelable


