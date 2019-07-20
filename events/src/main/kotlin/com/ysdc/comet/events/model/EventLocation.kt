package com.ysdc.comet.events.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class EventLocation(
    private val name: String?,
    private val city: String?,
    private val longitude: Double?,
    private val latitude: Double?
) : Parcelable