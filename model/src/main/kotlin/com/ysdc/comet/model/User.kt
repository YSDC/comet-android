package com.ysdc.comet.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @field:Json(name = "id")
    var id: String? = null,
    @field:Json(name = "firstName")
    var firstName: String = "",
    @field:Json(name = "lastName")
    var lastName: String = "",
    @field:Json(name = "email")
    var email: String = "",
    @field:Json(name = "phone")
    var phone: String = "",
    @field:Json(name = "jersey")
    var jersey: Int = 0,
    @field:Json(name = "role")
    var role: UserRole = UserRole.default(),
    @field:Json(name = "teamId")
    var teamId: Int = 0,
    @field:Json(name = "validated")
    var validated: Boolean = false
) : Parcelable


