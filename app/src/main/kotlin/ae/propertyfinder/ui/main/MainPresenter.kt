package ae.propertyfinder.ui.main

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.data.prefs.PrefsConstants.CONFIG_CHANGE_LOCALE
import com.ysdc.comet.common.data.prefs.PropertyFinderPreferences
import com.ysdc.comet.common.ui.base.BasePresenter

class MainPresenter<V : MainMvpView>(
    errorHandler: ErrorHandler,
    private val propertyFinderPreferences: PropertyFinderPreferences
) : BasePresenter<V>(errorHandler), MainMvpPresenter<V> {

    /**
     * checks for previously saved Configuration Changed and Restarts app
     */
    private fun checkForConfigurationChanges() {
        if (propertyFinderPreferences.getAsBoolean(CONFIG_CHANGE_LOCALE, false)) {
            propertyFinderPreferences.setBool(CONFIG_CHANGE_LOCALE, false)
            mvpView?.restartApp()
        }
    }
}