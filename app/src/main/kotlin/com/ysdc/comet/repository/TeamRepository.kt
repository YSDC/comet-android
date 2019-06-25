package com.ysdc.comet.repository

import com.ysdc.comet.model.Team
import com.ysdc.comet.network.DefaultNetworkServiceCreator
import io.reactivex.Single

class TeamRepository(
    private val defaultNetworkServiceCreator: DefaultNetworkServiceCreator
){
    fun getAvailableTeams() : Single<List<Team>> {

    }
}