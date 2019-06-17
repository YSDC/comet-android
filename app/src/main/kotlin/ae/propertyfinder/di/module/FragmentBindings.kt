package ae.propertyfinder.di.module

import ae.propertyfinder.account.ui.account.AccountFragment
import com.ysdc.comet.common.di.annotation.FragmentScope
import ae.propertyfinder.map.di.module.MapFragmentModule
import ae.propertyfinder.map.ui.map.MapFragment
import ae.propertyfinder.search.di.module.SearchFragmentModule
import ae.propertyfinder.search.ui.category.CategoryFragment
import ae.propertyfinder.search.ui.filter.FilterFragment
import ae.propertyfinder.search.ui.location.LocationFragment
import ae.propertyfinder.search.ui.search.SearchFragment
import ae.propertyfinder.search.ui.searchlist.SearchListFragment
import ae.propertyfinder.ui.save.SaveFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideAccountFragment(): AccountFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideSaveFragment(): SaveFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MapFragmentModule::class])
    abstract fun provideMapFragment(): MapFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    abstract fun provideSearchFragment(): SearchFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    abstract fun provideSearchListFragment(): SearchListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    abstract fun provideCategoryFragment(): CategoryFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    abstract fun provideLocationFragment(): LocationFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SearchFragmentModule::class])
    abstract fun provideFilterFragment(): FilterFragment
}
