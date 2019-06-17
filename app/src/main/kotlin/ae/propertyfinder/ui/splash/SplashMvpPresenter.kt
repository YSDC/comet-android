package ae.propertyfinder.ui.splash

import com.ysdc.comet.common.ui.base.MvpPresenter
import io.reactivex.Completable

/**
 * Created by david on 26/2/18.
 */

interface SplashMvpPresenter<V : SplashMvpView> : MvpPresenter<V> {
    fun loadConfiguration(): Completable
}
