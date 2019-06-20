package com.ysdc.comet.authentication.ui.register

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class RegisterPresenter<V : RegisterMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), RegisterMvpPresenter<V> {


}