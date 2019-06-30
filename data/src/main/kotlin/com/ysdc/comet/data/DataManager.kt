package com.ysdc.comet.data

import com.ysdc.comet.model.User
import io.reactivex.Single

interface DataManager{

    fun isClubTokenValid(token : String) : Single<Boolean>

    fun addOrUpdateUser(user: User) : Single<User>
}