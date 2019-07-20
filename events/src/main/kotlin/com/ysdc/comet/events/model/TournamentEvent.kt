package com.ysdc.comet.events.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
data class TournamentMatchEvent(
    @field:Json(name = "id")
    override val id: String,
    @field:Json(name = "name")
    override val name: String,
    @field:Json(name = "group")
    override val date: Date,
    override val imageUrl: String,
    override val location: EventLocation,
    override val state: EventState,
    override val type: EventType
) : Event, Parcelable