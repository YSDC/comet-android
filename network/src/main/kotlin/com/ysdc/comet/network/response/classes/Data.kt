package com.ysdc.comet.network.response.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "subtitle")
    var subtitle: String?,
    @Json(name = "regions")
    var regions: List<Region>?,
    @Json(name = "context")
    var context: Context?
)