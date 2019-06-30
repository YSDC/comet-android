package com.ysdc.comet.common.application


import com.ysdc.comet.model.Version
import com.squareup.moshi.Moshi

/**
 * General configuration whose data comes mainly from the Build data
 */
interface GeneralConfig {

    /**
     * Get the language used in the app. This values is define in the flavor, as a primary and secondary language.
     *
     * @return current language
     */
    fun localeString(): String

    /**
     * @return the package name of the app
     */
    fun applicationId(): String

    /**
     * @return formatted version of the application, used by the tracking
     */
    fun formattedVersion(): String

    /**
     * Get the flavor defined for the current build
     *
     * @return the name of the flavor selected
     */
    fun flavor(): String

    /**
     * @return the version name of the application
     */
    fun versionName(): String

    /**
     * @return the version code of the application
     */
    fun versionCode(): Int

    /**
     * @return an Version Object representing the Current version of the App
     */
    fun currentAppVersion(): Version

    /**
     * @return the language set on the phone
     */
    fun language(): String

    /**
     * @return the name of the Application Preference of the app
     */
    fun getPreferencesFileName(): String

    /**
     * @return true if the app is in debug mode
     */
    fun isDebug(): Boolean

    /**
     * @return an instance of Moshi, builded with the required customization
     */
    fun getMoshi(): Moshi

    /**
     * @return the store url of the app
     */
    fun storeUrl(): String

    /**
     * @return the club id
     */
    fun clubId(): Int
}