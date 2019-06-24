package com.ysdc.comet.network.service

import com.ysdc.comet.network.config.NetworkConstants.PARAM_API_KEY
import com.ysdc.comet.network.config.NetworkConstants.PARAM_SECRET
import com.ysdc.comet.network.config.NetworkConstants.URL_AUTH
import com.ysdc.comet.network.response.AuthenticationResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SwissFloorballService {

    @FormUrlEncoded
    @POST(URL_AUTH)
    fun authenticate(@Field(PARAM_SECRET) secret : String, @Field(PARAM_API_KEY) apiKey : String) : Single<AuthenticationResponse>

}