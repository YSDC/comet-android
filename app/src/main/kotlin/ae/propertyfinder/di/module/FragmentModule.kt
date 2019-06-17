package ae.propertyfinder.di.module

import ae.propertyfinder.account.ui.account.AccountMvpPresenter
import ae.propertyfinder.account.ui.account.AccountMvpView
import ae.propertyfinder.account.ui.account.AccountPresenter
import com.ysdc.comet.common.data.ErrorHandler
import ae.propertyfinder.analytics.AnalyticsManager
import com.ysdc.comet.common.data.repository.ConfigurationRepository
import com.ysdc.comet.common.data.repository.CountryRepository
import com.ysdc.comet.common.data.repository.NotificationRepository
import com.ysdc.comet.common.data.repository.UserRepository
import com.ysdc.comet.common.di.annotation.FragmentScope
import ae.propertyfinder.ui.save.SaveMvpPresenter
import ae.propertyfinder.ui.save.SaveMvpView
import ae.propertyfinder.ui.save.SavePresenter
import ae.propertyfinder.search.ui.search.SearchMvpPresenter
import ae.propertyfinder.search.ui.search.SearchMvpView
import ae.propertyfinder.search.ui.search.SearchPresenter
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    @FragmentScope
    fun provideSavePresenter(errorHandler: ErrorHandler): SaveMvpPresenter<SaveMvpView> {
        return SavePresenter(errorHandler)
    }

    @Provides
    @FragmentScope
    fun provideAccountPresenter(
        errorHandler: ErrorHandler, analyticsManager: AnalyticsManager, userRepository: UserRepository,
        notificationRepository: NotificationRepository, countryRepository: CountryRepository, configurationRepository: ConfigurationRepository
    ): AccountMvpPresenter<AccountMvpView> {
        return AccountPresenter(errorHandler, analyticsManager, userRepository, notificationRepository, countryRepository, configurationRepository)
    }
}
