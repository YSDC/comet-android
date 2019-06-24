package com.ysdc.comet.ui.main

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class MainPresenter<V : MainMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), MainMvpPresenter<V> {


}