package ae.propertyfinder.ui

import ae.propertyfinder.R
import ae.propertyfinder.model.Country
import ae.propertyfinder.model.NavigationItem
import com.ysdc.comet.common.data.repository.CountryRepository
import android.content.Context
import com.subkhansarif.bottomnavbar.BottomMenu

class UiManager(private val countryRepository: CountryRepository) {

    fun getMenuItems(context: Context): List<BottomMenu> {
        var menu: MutableList<BottomMenu> = ArrayList()
        when (countryRepository.getCurrentCountry()!!) {
            Country.UAE -> {
                menu.add(
                    BottomMenu(
                        NavigationItem.SEARCH.ordinal.toLong(),
                        context.getString(R.string.navbar_search),
                        R.drawable.ic_search_deselected,
                        "navbar_search.json"
                    )
                )
                menu.add(
                    BottomMenu(
                        NavigationItem.SAVE.ordinal.toLong(),
                        context.getString(R.string.navbar_saved),
                        R.drawable.ic_heart_deselected,
                        "navbar_save.json"
                    )
                )
                menu.add(
                    BottomMenu(
                        NavigationItem.ACCOUNT.ordinal.toLong(),
                        context.getString(R.string.navbar_profile),
                        R.drawable.ic_profile_deselected,
                        "navbar_account.json"
                    )
                )
            }else -> {
    //TODO
        }
        }
        return menu
    }
}