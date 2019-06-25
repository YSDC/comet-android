package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamDetailsResponse(
    @Json(name = "type")
    var type: String?,
    @Json(name = "data")
    var data: Data?
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "context")
        var context: Context?
    ) {
        @JsonClass(generateAdapter = true)
        data class Context(
            @Json(name = "league")
            var league: Int?,
            @Json(name = "game_class")
            var gameClass: Int?,
            @Json(name = "group")
            var group: String?
        )
    }
}