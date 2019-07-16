package com.ysdc.comet.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RankEntry (
    @field:Json(name = "team_id")
    val teamId : Int,
    @field:Json(name = "team_name")
    val teamName : String,
    @field:Json(name = "rank")
    var rank : Int,
    @field:Json(name = "picture")
    var picture : String,
    @field:Json(name = "played")
    var played : Int,
    @field:Json(name = "win")
    var win : Int,
    @field:Json(name = "winOverTime")
    var winOverTime : Int,
    @field:Json(name = "looseOverTime")
    var looseOverTime : Int,
    @field:Json(name = "loose")
    var loose : Int,
    @field:Json(name = "goals")
    var goals : String,
    @field:Json(name = "goalsDiff")
    var goalsDiff : Int,
    @field:Json(name = "points")
    var points : Int
): Parcelable

