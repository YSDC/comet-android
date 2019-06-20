package com.ysdc.comet.authentication.ui.activity

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

/**
 * Created by david on 1/25/18.
 */

class AuthenticationPresenter<V : AuthenticationMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), AuthenticationMvpPresenter<V> {

}
