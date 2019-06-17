package com.ysdc.comet.common.utils

import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import dagger.internal.Preconditions
import java.util.*

/**
 * Class to be used on a EditText or a SearchView, once the user types any character waits 400 ms, if is more than 400 ms return a callback to handle
 * the input string if it is less restarts timer in order to wait for more characters
 */
class ThrottledSearch @JvmOverloads constructor(
    private val mActivity: AppCompatActivity,
    private val mDelegate: Delegate,
    private val mDelayMilliseconds: Int = DEFAULT_DELAY
) {
    private var mSearchTerm: String? = null
    private var mTimer: Timer? = null

    interface Delegate {

        fun onThrottledSearch(newSearch: String?)

        companion object {
            val MIN_SEARCH_LENGTH = 0
        }
    }

    interface SearchViewQueryListener : Delegate {
        fun queryTextSubmit(newSearch: String)
    }

    init {
        Preconditions.checkNotNull(mActivity)
        mSearchTerm = EMPTY_STRING
    }

    fun onQueryTextSubmit(charSequence: CharSequence) {
        if (mDelegate is SearchViewQueryListener) {
            mDelegate.queryTextSubmit(charSequence.toString())
        }
    }

    fun onTextChanged(charSequence: CharSequence) {
        if (mTimer != null)
            mTimer!!.cancel()

        mTimer = Timer()
        mSearchTerm = charSequence.toString()

        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                mActivity.runOnUiThread { mDelegate.onThrottledSearch(mSearchTerm) }
            }
        }, mDelayMilliseconds.toLong())
    }

    fun attachTo(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                this@ThrottledSearch.onTextChanged(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
    }

    fun attachTo(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newSearch: String): Boolean {
                this@ThrottledSearch.onQueryTextSubmit(newSearch)
                return true
            }

            override fun onQueryTextChange(newSearch: String): Boolean {
                this@ThrottledSearch.onTextChanged(newSearch)
                return true
            }
        })
    }

    companion object {

        private val DEFAULT_DELAY = 150
    }
}
