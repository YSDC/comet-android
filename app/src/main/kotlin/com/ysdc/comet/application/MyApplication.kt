package com.ysdc.comet.application

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.akaita.java.rxjava2debug.RxJava2Debug
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.stetho.Stetho
import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.utils.AppConstants.CONFIG_CHANGE_LOCALE
import com.ysdc.comet.common.utils.CrashlyticsUtils
import com.ysdc.comet.di.component.ApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by david on 1/23/18.
 */

class MyApplication : MultiDexApplication(), HasActivityInjector, HasSupportFragmentInjector {

    lateinit var component: ApplicationComponent

    @Inject
    lateinit var dispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingAndroidFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var generalConfig: GeneralConfig

    @Inject
    lateinit var myPreferences: MyPreferences


    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidActivityInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidFragmentInjector

    /**
     * Called when the application is starting, before any other application objects have been
     * created. We initialize the external libraries here also, as it is always called, at the real
     * starting of the app, and only once.
     */
    override fun onCreate() {
        super.onCreate()

        initDagger()
        Fabric.with(
            this,
            Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build()
        )

        if (generalConfig.isDebug()) {
            //Init Timber
            Timber.plant(Timber.DebugTree())
            //Init Stetho debug bridge
            initStetho()
            RxJava2Debug.enableRxJava2AssemblyTracking(arrayOf("ae.propertyfinder"))

        } else {
            Timber.plant(CrashlyticsUtils.CrashlyticsTree())
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        )
    }

    private fun initDagger() {
        component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)
    }

    /**
     * Called by the system when the device configuration changes while your component is running.
     *
     * @param newConfig the new configuration
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        myPreferences.setBool(CONFIG_CHANGE_LOCALE, true)
    }

}
