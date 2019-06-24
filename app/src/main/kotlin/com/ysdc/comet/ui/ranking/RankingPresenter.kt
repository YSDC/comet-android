package com.ysdc.comet.ui.ranking

import com.ysdc.comet.common.data.ErrorHandler
import com.ysdc.comet.common.ui.base.BasePresenter

class RankingPresenter<V : RankingMvpView>(
    errorHandler: ErrorHandler
) : BasePresenter<V>(errorHandler), RankingMvpPresenter<V> {


}