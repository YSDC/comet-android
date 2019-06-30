package com.ysdc.comet.common.utils

class FormatterUtils {
    fun formatPhoneNumber(number : String) : String{
        var phoneNumber = number.replace("\\s".toRegex(), "")
        if(phoneNumber.startsWith("07")){
            return phoneNumber.replaceFirst("07", "+417", true)
        }else if(phoneNumber.startsWith("05")){
            return phoneNumber.replaceFirst("05", "+9715", true)
        }else{
            return phoneNumber
        }
    }
}