package ae.propertyfinder.di.module

import ae.propertyfinder.account.ui.login.LoginActivity
import ae.propertyfinder.account.ui.reset.ResetActivity
import com.ysdc.comet.common.di.annotation.ActivityScope
import ae.propertyfinder.ui.main.MainActivity
import ae.propertyfinder.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun provideResetActivity(): ResetActivity

}
