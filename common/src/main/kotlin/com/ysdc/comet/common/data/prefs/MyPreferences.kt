package com.ysdc.comet.common.data.prefs

import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import com.ysdc.comet.common.utils.CrashlyticsUtils
import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * Class in charge of all operation in the SharedPreferences. Expose a lot of getter and setter only
 */

class MyPreferences(context: Context, private val crashlyticsUtils: CrashlyticsUtils, prefsFilename: String) {

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(prefsFilename, Context.MODE_PRIVATE)

    operator fun contains(key: String): Boolean {
        return sharedPrefs.contains(key)
    }

    fun getAsString(key: String, defaultValue: String = EMPTY_STRING): String {
        return sharedPrefs.getString(key, defaultValue)!!
    }

    fun getAsString(key: String) : String? {
        return sharedPrefs.getString(key, null)
    }

    fun getAsInt(key: String, defaultValue: Int = Integer.MIN_VALUE): Int {
        return sharedPrefs.getInt(key, defaultValue)
    }

    fun getAsLong(key: String, defaultValue: Long = 0): Long {
        return sharedPrefs.getLong(key, defaultValue)
    }

    fun getAsBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defaultValue)
    }

    fun getAsSet(key: String): Set<String>? {
        return sharedPrefs.getStringSet(key, TreeSet<String>())

    }

    fun getAsDouble(key: String, defaultValue: Double = 0.0): Double {
        return if (!sharedPrefs.contains(key)) {
            defaultValue
        } else java.lang.Double.longBitsToDouble(sharedPrefs.getLong(key, 0))
    }

    fun resetPreferences() {
        val editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
    }

    fun setString(key: String, value: String) {
        val editor = sharedPrefs.edit()
        crashlyticsUtils.setKeyString(key.toLowerCase(), value)
        editor.putString(key, value)
        editor.apply()
    }

    fun setInt(key: String, value: Int) {
        val editor = sharedPrefs.edit()
        crashlyticsUtils.setKeyString(key.toLowerCase(), value.toString())
        editor.putInt(key, value)
        editor.apply()
    }

    fun setBool(key: String, value: Boolean) {
        val editor = sharedPrefs.edit()
        crashlyticsUtils.setKeyString(key.toLowerCase(), value.toString())
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setDouble(key: String, value: Double) {
        val editor = sharedPrefs.edit()
        crashlyticsUtils.setKeyString(key.toLowerCase(), value.toString())
        editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        editor.apply()
    }

    fun setLong(key: String, value: Long) {
        val editor = sharedPrefs.edit()
        crashlyticsUtils.setKeyString(key.toLowerCase(), value.toString())
        editor.putLong(key, value)
        editor.apply()
    }

    fun setSet(key: String, value: Set<String>) {
        val editor = sharedPrefs.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun remove(key: String) {
        val editor = sharedPrefs.edit()
        editor.remove(key)
        editor.apply()
    }

    fun removeKey(key: String) {
        val editor = sharedPrefs.edit()
        editor.remove(key)
        editor.apply()
    }
}
