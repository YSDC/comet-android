package com.ysdc.comet.data

import io.reactivex.Single

interface DataManager{

    fun teamExist(code : String) : Single<Boolean>
}