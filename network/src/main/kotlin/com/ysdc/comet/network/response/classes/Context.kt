package com.ysdc.comet.network.response.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Context(
    @Json(name = "league")
    var league: Int?,
    @Json(name = "game_class")
    var gameClass: Int?,
    @Json(name = "group")
    var group: String?
)