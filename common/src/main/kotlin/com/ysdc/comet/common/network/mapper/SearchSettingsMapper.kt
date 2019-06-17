package com.ysdc.comet.common.network.mapper

import com.ysdc.comet.common.R
import com.ysdc.comet.common.network.model.searchsettings.SearchSettingsResponse
import com.ysdc.comet.common.utils.AppConstants
import ae.propertyfinder.model.SearchSettings
import android.content.Context
import com.ysdc.buttongroup.firstIndexOrNull

class SearchSettingsMapper(val context: Context) {
    fun map(searchSettingsResponse: SearchSettingsResponse): SearchSettings {
        val searchSettingsBuilder = SearchSettings.Builder()
        searchSettingsResponse.sorts.choices
            .filter { it.value.isNotEmpty() }
            .map { searchSettingsBuilder.addSort(SearchSettings.Entry(it.value, it.label)) }
        if((searchSettingsResponse.sorts.value as String).isNotEmpty()){
            searchSettingsBuilder.defaultSortIndex = searchSettingsResponse.sorts.choices.firstIndexOrNull {
                it.value == searchSettingsResponse.sorts.value as String
            }
        }

        searchSettingsResponse.minPrice.choices.union(searchSettingsResponse.maxPrice.choices)
            .filter { it.value.isNotEmpty() }
            .map { searchSettingsBuilder.addPrice(SearchSettings.Entry(it.value, it.label)) }
        if(searchSettingsBuilder.prices.isNotEmpty()){
            searchSettingsBuilder.prices.add(0, SearchSettings.Entry(AppConstants.KEY_FROM_ANY_FILTER, context.getString(R.string.filter_any_name)))
            searchSettingsBuilder.prices.add(SearchSettings.Entry(AppConstants.KEY_TO_ANY_FILTER, context.getString(R.string.filter_any_name)))
        }
        searchSettingsResponse.minArea.choices.union(searchSettingsResponse.maxArea.choices)
            .filter { it.value.isNotEmpty() }
            .map { searchSettingsBuilder.addArea(SearchSettings.Entry(it.value, it.label)) }
        if(searchSettingsBuilder.areas.isNotEmpty()){
            searchSettingsBuilder.areas.add(0, SearchSettings.Entry(AppConstants.KEY_FROM_ANY_FILTER, context.getString(R.string.filter_any_name)))
            searchSettingsBuilder.areas.add(SearchSettings.Entry(AppConstants.KEY_TO_ANY_FILTER, context.getString(R.string.filter_any_name)))
        }
        searchSettingsResponse.priceTypes.choices
            .filter { it.value.isNotEmpty() }
            .map {
                // Default Any PriceType
                searchSettingsBuilder.addpriceType(SearchSettings.Entry(it.value, it.label))
            }
        if((searchSettingsResponse.priceTypes.value != null) && (searchSettingsResponse.priceTypes.value as String).isNotEmpty()){
            searchSettingsBuilder.defaultPriceTypeIndex = searchSettingsResponse.priceTypes.choices.firstIndexOrNull {
                it.value == searchSettingsResponse.priceTypes.value
            }
        }

        searchSettingsResponse.propertyTypes.choices
            .filter { it.value.isNotEmpty() }
            .map { searchSettingsBuilder.addPropertyType(SearchSettings.Entry(it.value, it.label)) }
        if (searchSettingsBuilder.propertyTypes.isEmpty()) {
            searchSettingsBuilder.defaultPropertyTypeIndex = 0
            searchSettingsBuilder.propertyTypes.add(SearchSettings.Entry(AppConstants.KEY_NOT_APPLICABLE_FILTER, context.getString(R.string.filter_not_applicable)))
        }else {
            searchSettingsBuilder.propertyTypes.add(0, SearchSettings.Entry(AppConstants.KEY_FROM_ANY_FILTER, context.getString(R.string.filter_any_name)))
            searchSettingsBuilder.defaultPropertyTypeIndex = searchSettingsResponse.propertyTypes.choices.firstIndexOrNull {
                it.value == (searchSettingsResponse.propertyTypes.value as String)
            }
        }

        searchSettingsResponse.minBed.choices.union(searchSettingsResponse.maxBed.choices)
            .filter { it.value.isNotEmpty() }
            .map { searchSettingsBuilder.addBedroom(SearchSettings.Entry(it.value, if(it.value?.toInt() > 0) it.value else it.label)) }
        if (searchSettingsBuilder.bedrooms.isEmpty()) {
            searchSettingsBuilder.bedrooms.add(SearchSettings.Entry(AppConstants.KEY_NOT_APPLICABLE_FILTER, context.getString(R.string.filter_not_applicable)))
        }else{
            searchSettingsBuilder.bedrooms.add(0, SearchSettings.Entry(AppConstants.KEY_FROM_ANY_FILTER, context.getString(R.string.filter_any_name)))
        }

        searchSettingsResponse.amenities.choices
            .filter { it.value.isNotEmpty() }
            .map { searchSettingsBuilder.addAmenity(SearchSettings.Entry(it.value, it.label)) }

        if (searchSettingsBuilder.amenities.isEmpty()) {
            searchSettingsBuilder.amenities.add(SearchSettings.Entry(AppConstants.KEY_NOT_APPLICABLE_FILTER, context.getString(R.string.filter_not_applicable)))
        }else{
            searchSettingsBuilder.amenities.add(0, SearchSettings.Entry(AppConstants.KEY_FROM_ANY_FILTER, context.getString(R.string.filter_any_name)))
        }

        searchSettingsResponse.furnishing.choices
            .filter { it.value.isNotEmpty() && it.value.toInt() != 0 }
            // Default All Filter
            .map { searchSettingsBuilder.addfurnishing(SearchSettings.Entry(it.value, it.label)) }
        if (searchSettingsBuilder.furnishing.isEmpty()) {
            searchSettingsBuilder.defaultFurnishingIndex = 0
            searchSettingsBuilder.furnishing.add(SearchSettings.Entry(AppConstants.KEY_NOT_APPLICABLE_FILTER, context.getString(R.string.filter_not_applicable)))
        }else {
            searchSettingsBuilder.defaultFurnishingIndex = searchSettingsResponse.furnishing.choices.firstIndexOrNull {
                it.value == (searchSettingsResponse.furnishing.value as String)
            }
            searchSettingsBuilder.furnishing.add(0, SearchSettings.Entry(AppConstants.KEY_FROM_ANY_FILTER, context.getString(R.string.filter_any_name)))
        }


        return searchSettingsBuilder.build()
    }
}