package com.ysdc.comet.common.utils

import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import ae.propertyfinder.model.AreaUnit
import android.content.res.Resources
import java.text.DecimalFormat

private const val ONE_SQFT_IN_SQM = 0.09290304

class AreaUtils {

    /**
     * Convert SQM or SQFT to the other format
     * @param resources required to get the string of the area unit
     * @param areaSize the value to convert
     * @param areaUnit the unit to use in the return value
     * @return the value with the unit as a string
     */
    fun generateAreaLabel(resources: Resources, areaSize: Int, areaUnit: AreaUnit): String {
        val formatter = DecimalFormat("#,###,###")
        return when (areaUnit) {
            AreaUnit.SQM -> "${formatter.format(areaSize.toDouble() * ONE_SQFT_IN_SQM)} ${resources.getString(areaUnit.key)}"
            AreaUnit.SQFT -> "${formatter.format(areaSize.toDouble() / ONE_SQFT_IN_SQM)}  ${resources.getString(areaUnit.key)}"
            else -> EMPTY_STRING
        }
    }

    /**
     * Convert SQM or SQFT to the other format
     * @param resources required to get the string of the area unit
     * @param areaSize the value to convert
     * @param areaUnit the unit to use in the return value
     * @return the value with the unit as a string
     */
    fun convertValue(areaSize: Int, areaUnit: AreaUnit): String {
        val formatter = DecimalFormat("#,###,###")
        return when (areaUnit) {
            AreaUnit.SQM -> "${formatter.format(areaSize.toDouble() * ONE_SQFT_IN_SQM)} "
            AreaUnit.SQFT -> "${formatter.format(areaSize.toDouble() / ONE_SQFT_IN_SQM)} "
            else -> EMPTY_STRING
        }
    }
}