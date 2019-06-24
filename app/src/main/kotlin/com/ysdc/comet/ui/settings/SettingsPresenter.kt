package com.ysdc.comet.ui.settings

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class SettingsPresenter<V : SettingsMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), SettingsMvpPresenter<V> {


}