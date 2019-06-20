package com.ysdc.comet.authentication

import ae.propertyfinder.common.data.ErrorHandler
import ae.propertyfinder.common.ui.base.BasePresenter

class ValidatePresenter<V : ValidateMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), ValidateMvpPresenter<V> {


}