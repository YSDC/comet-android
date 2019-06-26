package com.ysdc.comet.data

import com.github.ajalt.timberkt.Timber
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class ConfigManager(
    private val remoteConfig: FirebaseRemoteConfig
){

    init {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {task ->
                Timber.d{"Remote Config: fetchAndActivate completed"}
                if)
            }
            .addOnFailureListener {
                Timber.d{"Remote Config: fetchAndActivate failed"}

            }
            .addOnCanceledListener {
                Timber.d{"Remote Config: fetchAndActivate canceled"}
            }
    }
}