package com.ysdc.comet.authentication

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class ValidatePresenter<V : ValidateMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), ValidateMvpPresenter<V> {


}