package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MatchResponse(
    @Json(name = "type")
    var type: String?,
    @Json(name = "data")
    var data: Data?
) {
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "subtitle")
        var subtitle: String,
        @Json(name = "regions")
        var regions: List<Regions>?
    ) {
        @JsonClass(generateAdapter = true)
        data class Regions(
            @Json(name = "rows")
            var rows: List<Row>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Row(
                @Json(name = "cells")
                var cells: List<Cell>?
            )
        }
    }
}