package com.ysdc.comet.common.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipePager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var swipeEnabled: Boolean = false

    init {
        this.swipeEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.swipeEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.swipeEnabled && super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.swipeEnabled = enabled
    }
}