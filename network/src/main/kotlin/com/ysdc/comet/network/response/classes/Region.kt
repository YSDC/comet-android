package com.ysdc.comet.network.response.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Region(
    @Json(name = "rows")
    var rows: List<Row>?
)