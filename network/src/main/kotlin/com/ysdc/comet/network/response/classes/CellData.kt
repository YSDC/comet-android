package com.ysdc.comet.network.response.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CellData(
    @Json(name = "team")
    var team: Team?
) {
    @JsonClass(generateAdapter = true)
    data class Team(
        @Json(name = "id")
        var id: Int?
    )
}