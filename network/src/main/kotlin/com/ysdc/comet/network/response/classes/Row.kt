package com.ysdc.comet.network.response.classes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Row(
    @Json(name = "cells")
    var cells: List<Cell>?,
    @Json(name = "data")
    var cellData: CellData?,
    @Json(name = "link")
    var teamId: Link?
)