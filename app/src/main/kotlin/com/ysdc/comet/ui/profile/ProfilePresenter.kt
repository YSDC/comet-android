package com.ysdc.comet.ui.profile

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class ProfilePresenter<V : ProfileMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), ProfileMvpPresenter<V> {


}