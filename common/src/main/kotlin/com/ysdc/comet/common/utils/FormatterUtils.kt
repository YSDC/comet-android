package com.ysdc.comet.common.utils

class FormatterUtils {
    fun formatPhoneNumber(number : String) : String{
        if(number.startsWith("07")){
            return number.replaceFirst("07", "+417", true)
        }else if(number.startsWith("05")){
            return number.replaceFirst("05", "+9715", true)
        }else{
            return number
        }
    }
}