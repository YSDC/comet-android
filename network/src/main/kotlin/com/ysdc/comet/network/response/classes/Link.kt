package com.ysdc.comet.network.response.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Link(
    @Json(name = "type")
    var type: String?,
    @Json(name = "page")
    var page: String?,
    @Json(name = "ids")
    var ids: List<Int>?,
    @Json(name = "x")
    var longitude: Float?,
    @Json(name = "y")
    var latitude: Float?
)