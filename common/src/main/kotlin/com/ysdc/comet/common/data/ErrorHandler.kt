package com.ysdc.comet.common.data

import android.content.Context
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Class used to communicate error from isolated classes to the activities/fragments
 * TODO: Check if still useful with new implementation
 */

class ErrorHandler(private val context: Context) {
    private val errorSubject = PublishSubject.create<String>()

    fun addError(errorStringReference: Int) {
        errorSubject.onNext(context.getString(errorStringReference))
    }

    fun subscribeGeneralError(): Observable<String> {
        return errorSubject
    }
}
