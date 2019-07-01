package com.ysdc.comet.repositories

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.PREFS_USER
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.model.User
import io.reactivex.Completable

class UserRepository(
    private val dataManager: DataManager,
    private val preferences: MyPreferences,
    private val generalConfig: GeneralConfig
) {

    fun getUser(): User? {
        val userJson = preferences.getAsString(PREFS_USER)
        return userJson?.let { generalConfig.getMoshi().adapter(User::class.java).fromJson(userJson) }
    }

    fun createUser(user: User): Completable {
        return dataManager.addUser(user)
            .doOnSuccess { userCreated ->
                val userJson = generalConfig.getMoshi().adapter(User::class.java).toJson(userCreated)
                preferences.setString(PREFS_USER, userJson)
            }.ignoreElement()
    }

    fun updateUser(user: User): Completable {
        return dataManager.updateUser(user)
            .doOnComplete {
                updateUserLocally(user)
            }
    }

    fun updateUserLocally(user : User){
        val userJson = generalConfig.getMoshi().adapter(User::class.java).toJson(user)
        preferences.setString(PREFS_USER, userJson)
    }
}