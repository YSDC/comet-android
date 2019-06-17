package com.ysdc.comet.common.utils.extension

import com.ysdc.comet.common.utils.AppConstants
import android.util.Patterns


/**
 * Build a string representation of a key-values pair (the key being the string calling this method). Example: if key is foo and values is bar the result
 * will be 'foo=bar'.
 *
 * @param value the values
 * @return the concat of the key values as String
 */
fun String.buildStringForValue(value: String): String {
    return this.plus("=").plus(value)
}

/**
 * Considering the string calling this method a json file, we remove all possible entries with a key corresponding to an entry of the list given in
 * parameter.
 * @param values the keys to look for and remove from the json file
 * @return the json without any entries given in param
 */
fun String.removeSensitiveValues(values: List<String>): String {
    return this
//todo
//    if (TextUtils.isEmpty(this)) {
//        return "{ bodyJson: \"response is String Empty\" }"
//    }
//
//    val moshi = Moshi.Builder().build()
//    val map = moshi.adapter<Map<String,Any>>(Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)).fromJson(this) ?: HashMap()
//    for((key, values) in map){
//
//    }
//    try {
//        val o = JsonParser().parse(this).asJsonObject
//
//        for (key in values) {
//
//            val data = o.getAsJsonObject("data")
//            if (data != null) {
//                data.remove(key)
//                data.getAsJsonObject("attributes")?.remove(key)
//            }
//        }
//        return o.toString()
//
//    } catch (e: Exception) {
//        e.printStackTrace()
//        return "{ bodyJson: \"exception while removing sensitive values\" }"
//    }

}

/**
 * Convert an array of byte to an Hexadecimal representation. It will take care
 * to always use two characters per byte and ensure a correct size
 *
 * @param bytes the array to convert
 * @return a string that represent the array of byte in hexa
 */
fun String.Companion.byteToHexString(bytes: ByteArray): String {
    val result = AppConstants.EMPTY_STRING
    for (b in bytes) {
        result.plus(String.format("%02x", b))
    }
    return result
}

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return this.isNotEmpty() && this.matches(Regex.fromLiteral("(?=.*?\\d.*\\d)[\\S]{6,}"))
}
