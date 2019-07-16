package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RankingResponse(
    @Json(name = "type")
    var type: String?,
    @Json(name = "data")
    var data: Data?
) {
    @JsonClass(generateAdapter = true)
    data class Data(
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
                var cells: List<Cell>?,
                @Json(name = "data")
                var rowData: RowData?
            ) {

                @JsonClass(generateAdapter = true)
                data class RowData(
                    @Json(name = "team")
                    var team: Team?
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Team(
                        @Json(name = "id")
                        var id: Int?
                    )
                }

                @JsonClass(generateAdapter = true)
                data class Cell(
                    @Json(name = "text")
                    var text: List<String>?,
                    @Json(name = "link")
                    var link: Link?,
                    @Json(name = "image")
                    var image: Image?
                )

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

                @JsonClass(generateAdapter = true)
                data class Image(
                    @Json(name = "url")
                    var url: String?
                )
            }
        }
    }
}