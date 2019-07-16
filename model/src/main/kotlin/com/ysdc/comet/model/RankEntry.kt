package com.ysdc.comet.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RankEntry(
    @field:Json(name = "team_id")
    val teamId: Int,
    @field:Json(name = "rank")
    val rank: Int,
    @field:Json(name = "picture")
    val picture: String?,
    @field:Json(name = "team_name")
    val teamName: String,
    @field:Json(name = "played")
    val played: Int,
    @field:Json(name = "win")
    val win: Int,
    @field:Json(name = "winOverTime")
    val winOverTime: Int,
    @field:Json(name = "looseOverTime")
    val looseOverTime: Int,
    @field:Json(name = "loose")
    val loose: Int,
    @field:Json(name = "goals")
    val goals: String,
    @field:Json(name = "goalsDiff")
    val goalsDiff: Int,
    @field:Json(name = "points")
    val points: Int
) : Parcelable {
    data class Builder(
        var teamId: Int = 0,
        var teamName: String = "",
        var rank: Int = 1,
        var picture: String? = null,
        var played: Int = 0,
        var win: Int = 0,
        var winOverTime: Int = 0,
        var looseOverTime: Int = 0,
        var loose: Int = 0,
        var goals: String = "0:0",
        var goalsDiff: Int = 0,
        var points: Int = 0
    ) {
        fun teamId(value: Int) = apply { this.teamId = value }
        fun teamName(value: String) = apply { this.teamName = value }
        fun rank(value: Int) = apply { this.rank = value }
        fun picture(value: String) = apply { this.picture = value }
        fun played(value: Int) = apply { this.played = value }
        fun win(value: Int) = apply { this.win = value }
        fun winOverTime(value: Int) = apply { this.winOverTime = value }
        fun looseOverTime(value: Int) = apply { this.looseOverTime = value }
        fun loose(value: Int) = apply { this.loose = value }
        fun goals(value: String) = apply { this.goals = value }
        fun goalsDiff(value: Int) = apply { this.goalsDiff = value }
        fun points(value: Int) = apply { this.points = value }
        fun build() = RankEntry(teamId, teamName, rank, picture, played, win, winOverTime, looseOverTime, loose, goals, goalsDiff, points)
    }
}

