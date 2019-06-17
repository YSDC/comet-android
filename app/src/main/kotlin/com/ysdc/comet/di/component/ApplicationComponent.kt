package com.ysdc.comet.di.component

import com.ysdc.comet.application.MyApplication
import com.ysdc.comet.common.di.module.SharedApplicationModule
import com.ysdc.comet.common.di.module.SharedNetworkModule
import com.ysdc.comet.common.di.module.SharedUtilsModule
import com.ysdc.comet.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindings::class,
        AnalyticsModule::class,
        ApplicationModule::class,
        SharedApplicationModule::class,
        FragmentBindings::class,
        NetworkModule::class,
        RepositoryModule::class,
        SharedNetworkModule::class,
        SharedUtilsModule::class]
)
interface ApplicationComponent {

    fun inject(application: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        fun build(): ApplicationComponent
    }
}