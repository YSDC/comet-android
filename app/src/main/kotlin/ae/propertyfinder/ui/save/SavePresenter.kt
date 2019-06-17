package ae.propertyfinder.ui.save

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class SavePresenter<V : SaveMvpView>(errorHandler: ErrorHandler) : BasePresenter<V>(errorHandler), SaveMvpPresenter<V> {


}
