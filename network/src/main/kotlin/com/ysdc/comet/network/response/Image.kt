package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "url")
    var url: String?
)