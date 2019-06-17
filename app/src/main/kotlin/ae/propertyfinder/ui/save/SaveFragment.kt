package ae.propertyfinder.ui.save

import ae.propertyfinder.R
import com.ysdc.comet.common.ui.base.BaseFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

class SaveFragment : BaseFragment(), SaveMvpView {

    private var tabId: Int = 0

    override val screenName: String = "todo"
    override val customTitle: String
        get() = getString(R.string.saved_title)
    override val isActionBarVisible: Boolean = true

    @Inject
    lateinit var presenter: SaveMvpPresenter<SaveMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_save, container, false)

        if (arguments != null) {
            tabId = arguments!!.getInt(EXTRA_TAB)
        }

        presenter.onAttach(this)
        if (baseActivity!!.curTabId == tabId) {
            baseActivity!!.supportActionBar?.title = customTitle
        }

        return view
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun setUp(view: View) {
        //Nothing to do
    }

    override fun shouldToolbarBeElevated(): Boolean {
        return true
    }

    companion object {
        private const val EXTRA_TAB = "EXTRA_TAB"

        fun newInstance(tab: Int): SaveFragment {
            val fragment = SaveFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_TAB, tab)
            fragment.arguments = bundle
            return fragment
        }
    }
}