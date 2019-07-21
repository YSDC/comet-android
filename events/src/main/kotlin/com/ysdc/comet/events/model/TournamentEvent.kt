package com.ysdc.comet.events.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TournamentMatchEvent(
    @field:Json(name = "id")
    override val id: String,
    @field:Json(name = "name")
    override val name: String,
    @field:Json(name = "date")
    override val date: String,
    @field:Json(name = "time")
    override val time: String,
    @field:Json(name = "imageUrl")
    override val imageUrl: String,
    @field:Json(name = "location")
    override val location: EventLocation,
    @field:Json(name = "state")
    override val state: EventState,
    @field:Json(name = "type")
    override val type: EventType,
    @field:Json(name = "homeTeam")
    val homeTeam: String,
    @field:Json(name = "awayTeam")
    val awayTeam: String,
    @field:Json(name = "score")
    val score: String
) : Event, Parcelable