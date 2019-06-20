package com.ysdc.comet.authentication

import ae.propertyfinder.common.data.ErrorHandler
import ae.propertyfinder.common.ui.base.BasePresenter

class teamPresenter<V : teamMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), teamMvpPresenter<V> {


}