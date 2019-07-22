package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.ysdc.comet.network.response.classes.Data

@JsonClass(generateAdapter = true)
data class StandardResponse(
    @Json(name = "type")
    var type: String?,
    @Json(name = "data")
    var data: Data?
)