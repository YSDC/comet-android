package com.ysdc.comet.common.network.model.searchsettings

import androidx.annotation.VisibleForTesting
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchSettingsResponse(
    @Json(name = "c")
    val categories: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "ob")
    val sorts: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "t")
    val propertyTypes: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "pf")
    val minPrice: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "pt")
    val maxPrice: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "bf")
    val minBed: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "bt")
    val maxBed: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "af")
    val minArea: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "at")
    val maxArea: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "rp")
    val priceTypes: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "fu")
    val furnishing: StringSettingsFilter = StringSettingsFilter(),
    @Json(name = "am")
    val amenities: StringSettingsFilter = StringSettingsFilter(),
    @field:Json(name = "errors")
    val errors: MutableList<Error> = ArrayList()
) {

//    @JsonClass(generateAdapter = true)
//    data class IntSettingsFilter(
//        @Json(name = "choices")
//        val choices: List<IntSettingsChoice>
//    )

    @JsonClass(generateAdapter = true)
    data class StringSettingsFilter(
        @Json(name = "value")
        val value: Any? = null,
        @Json(name = "choices")
        val choices: List<StringSettingsChoice> = ArrayList()
    )

    @JsonClass(generateAdapter = true)
    data class StringSettingsChoice(
        @Json(name = "value")
        val value: String,
        @Json(name = "request_value")
        var requestValue: String?,
        @Json(name = "label")
        val label: String
    )


    data class Error(@field:Json(name = "detail") val detail: String? = null)

    fun getErrorDetail(): String? {
        return errors[0].detail
    }

    fun hasErrors(): Boolean {
        return !errors.isEmpty()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun addError() {
        errors.add(Error())
    }
}