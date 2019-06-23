package com.ysdc.comet.di.module

import com.google.firebase.firestore.FirebaseFirestore
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.data.FirestoreDataManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Singleton
    @Provides
    fun provideFirestore(generalConfig: GeneralConfig): FirebaseFirestore {
        FirebaseFirestore.setLoggingEnabled(generalConfig.isDebug())
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideDataManager(firebaseFirestore: FirebaseFirestore) : DataManager{
        return FirestoreDataManager(firebaseFirestore)
    }
}
