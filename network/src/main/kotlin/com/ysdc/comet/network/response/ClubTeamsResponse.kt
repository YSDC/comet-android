package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClubTeamsResponse(
    @Json(name = "type")
    var type: String?,
    @Json(name = "entries")
    var entries: List<Entries>?
) {
    @JsonClass(generateAdapter = true)
    data class Entries(
        @Json(name = "text")
        var teamName: String?,
        @Json(name = "set_in_context")
        var msg: EntriesDetail?
    ) {
        @JsonClass(generateAdapter = true)
        data class EntriesDetail(
            @Json(name = "team_id")
            var teamId: Int?
        )
    }
}