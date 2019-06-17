package ae.propertyfinder.ui.splash

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.PrefsConstants.CONFIG_CHANGE_LOCALE
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.data.repository.ConfigurationRepository
import com.ysdc.comet.common.ui.base.BasePresenter
import ae.propertyfinder.search.data.repository.SearchRepository
import io.reactivex.Completable

/**
 * Created by david on 1/25/18.
 */

class SplashPresenter<V : SplashMvpView>(
    errorHandler: ErrorHandler,
    private val propertyFinderPreferences: PropertyFinderPreferences,
    private val configurationRepository: ConfigurationRepository,
    private val searchRepository: SearchRepository
) : BasePresenter<V>(errorHandler), SplashMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        propertyFinderPreferences.setBool(CONFIG_CHANGE_LOCALE, false)
    }

    override fun loadConfiguration(): Completable {
        return searchRepository.getSearchFilters()
            .firstOrError()
            .doAfterSuccess {
                searchRepository.loadLocations()
            }
            .flatMapCompletable{filters -> configurationRepository.loadConfiguration(filters)}
    }
}
