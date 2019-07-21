package com.ysdc.comet.network.response

import com.squareup.moshi.Json

data class Cell(
    @Json(name = "text")
    var text: List<String>?,
    @Json(name = "link")
    var link: Link?,
    @Json(name = "image")
    var image: Image?
)