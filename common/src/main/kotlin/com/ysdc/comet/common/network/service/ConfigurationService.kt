package com.ysdc.comet.common.network.service

import com.ysdc.comet.common.network.config.NetworkConstants
import com.ysdc.comet.common.network.model.searchsettings.SearchSettingsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


interface ConfigurationService {

    @Headers(NetworkConstants.HEADER_CONTENT_ADVANCED_JSON)
    @GET(NetworkConstants.WEBSITE_API + NetworkConstants.SEARCH_FORM)
    fun getSearchSettings(@Path(NetworkConstants.LOCALE) locale: String, @QueryMap(encoded = true) params: Map<String, String>):
            Single<SearchSettingsResponse>

}