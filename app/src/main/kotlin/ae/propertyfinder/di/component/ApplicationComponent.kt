package ae.propertyfinder.di.component

import ae.propertyfinder.application.PropertyFinderApplication
import com.ysdc.comet.common.di.module.SharedApplicationModule
import com.ysdc.comet.common.di.module.SharedNetworkModule
import com.ysdc.comet.common.di.module.SharedUtilsModule
import ae.propertyfinder.di.module.*
import ae.propertyfinder.search.di.module.SearchNetworkModule
import ae.propertyfinder.search.di.module.SearchRepositoryModule
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
        SharedUtilsModule::class,
        SearchRepositoryModule::class,
        SearchNetworkModule::class]
)
interface ApplicationComponent {

    fun inject(application: PropertyFinderApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PropertyFinderApplication): Builder

        fun build(): ApplicationComponent
    }
}