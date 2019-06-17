package com.ysdc.comet.common.ui.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Infinite list view listener
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    /**
     * The total number of items in the dataset after the last load
     */
    private var mPreviousTotal = 0
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (totalItemCount == visibleItemCount + firstVisibleItem && mLoading) {
            endOfListReached()
        }

        if (mLoading && (totalItemCount > mPreviousTotal)) {
            mLoading = false
            mPreviousTotal = totalItemCount
        }
        val visibleThreshold = 20
        if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            loadMorelistContent()
            mLoading = true
        }
    }

    abstract fun loadMorelistContent()

    abstract fun endOfListReached()

    fun reset() {
        mPreviousTotal = 0
        mLoading = true
    }
}