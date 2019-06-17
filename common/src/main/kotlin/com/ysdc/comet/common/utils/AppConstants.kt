package com.ysdc.comet.common.utils

/**
 * Contains the constants used in different part of our application.
 */

object AppConstants {

    //SharedFragmentModule
    const val EMPTY_STRING = ""
    const val EMPTY_FIELD = "-"
    const val SPACE_STRING = " "
    const val LIST_SEPARATOR = ","

    // Filter
    const val KEY_NOT_APPLICABLE_FILTER = "55555"
    const val KEY_FROM_ANY_FILTER = "11111"
    const val KEY_TO_ANY_FILTER = "99999"
    const val NUMBER_THRESHOLD_SHORT_FORMAT = 150000

    //Loggin categories
    const val LOGGING_CATEGORY_NETWORK = "[Network]"
    const val LOGGING_CATEGORY_ACTIVITY_LIFECYCLE = "[ActivityLifecycle]"
    const val LOGGING_CATEGORY_FRAGMENT_LIFECYCLE = "[FragmentLifecycle]"
    const val LOGGING_CATEGORY_INTERACTIONS = "[ViewInteractions]"
    const val LOGGING_CATEGORY_NOTIFICATION = "[Notification]"
    const val LOGGING_CATEGORY_DEEPLINK = "[DeepLink]"

    //facebook
    val FB_PERMISSION = arrayOf("public_profile, email")
    const val FB_EMAIL_PERMISSION = "email"
    const val FB_PUBLIC_PROFILE_PERMISSION = "public_profile"

    const val ALPHA_NUM_VALUES = "[^A-Za-z0-9\\p{InArabic} ]"
    //We need to replace first 'VAL' before using it
    const val REGEX_PARAM = "VAL"
    const val LOCATION_REGEX = "\\s\\(*VAL[^\\s]*|^\\(*VAL[^\\s]*"
}
