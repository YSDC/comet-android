package com.ysdc.comet.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AuthenticationResponse(
    @Json(name = "token")
    var token: String?
) {
    @JsonClass(generateAdapter = true)
    data class Status(
        @Json(name = "code")
        var code: Int?,
        @Json(name = "msg")
        var msg: String?
    )
}