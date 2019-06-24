package com.ysdc.comet.ui.event

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class EventPresenter<V : EventMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), EventMvpPresenter<V> {


}