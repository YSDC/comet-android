package com.ysdc.comet.ui.event

import com.ysdc.comet.common.ui.base.BaseFragment
import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ysdc.comet.R
import javax.inject.Inject

class EventFragment : BaseFragment(), EventMvpView {


    override val customTitle: String = EMPTY_STRING
    override val screenName: String = "todo"
    override val isActionBarVisible: Boolean = false

    @Inject
    lateinit var presenter: EventMvpPresenter<EventMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        presenter.onAttach(this)
        baseActivity!!.supportActionBar?.title = customTitle
        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun setUp(view: View) {

    }

    override fun shouldToolbarBeElevated(): Boolean {
        return true
    }

    companion object {
        fun newInstance(): EventFragment {
            return EventFragment()
        }
    }
}