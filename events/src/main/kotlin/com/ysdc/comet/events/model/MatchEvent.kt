package com.ysdc.comet.events.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MatchEvent(
    @field:Json(name = "id")
    override val id: String,
    @field:Json(name = "name")
    override val name: String,
    @field:Json(name = "date")
    override val date: String,
    @field:Json(name = "time")
    override val time: String,
    @field:Json(name = "location")
    override val location: EventLocation?,
    @field:Json(name = "state")
    override val state: EventState,
    @field:Json(name = "type")
    override val type: EventType,
    @field:Json(name = "homeTeamName")
    val homeTeam: String,
    @field:Json(name = "awayTeamName")
    val awayTeam: String,
    @field:Json(name = "homeLogo")
    val homeLogo: String?,
    @field:Json(name = "awayLogo")
    val awayLogo: String?,
    @field:Json(name = "score")
    val score: String
) : Event, Parcelable {
    data class Builder(
        var id: String = "",
        var name: String = "",
        var date: String = "",
        var time: String = "",
        var homeLogo: String? = null,
        var awayLogo: String? = null,
        var location: EventLocation? = null,
        var state: EventState = EventState.TO_COME,
        var type: EventType = EventType.OTHER,
        var homeTeam: String = "",
        var awayTeam: String = "",
        var score: String = ""
    ) {
        fun id(value: String) = apply { this.id = value }
        fun name(value: String) = apply { this.name = value }
        fun date(value: String) = apply { this.date = value }
        fun time(value: String) = apply { this.time = value }
        fun location(value: EventLocation) = apply { this.location = value }
        fun state(value: EventState) = apply { this.state = value }
        fun type(value: EventType) = apply { this.type = value }
        fun homeTeam(value: String) = apply { this.homeTeam = value }
        fun awayTeam(value: String) = apply { this.awayTeam = value }
        fun homeLogo(value: String) = apply { this.homeLogo = value }
        fun awayLogo(value: String) = apply { this.awayLogo = value }
        fun score(value: String) = apply { this.score = value }
        fun build() = MatchEvent(id, name, date, time, location, state, type, homeTeam, awayTeam, homeLogo, awayLogo, score)
    }
}