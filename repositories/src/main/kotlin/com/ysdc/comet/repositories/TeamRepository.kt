package com.ysdc.comet.repositories

import com.ysdc.comet.model.Team
import com.ysdc.comet.network.DefaultNetworkServiceCreator
import com.ysdc.comet.network.mapper.TeamMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TeamRepository(
    private val defaultNetworkServiceCreator: DefaultNetworkServiceCreator
) {
    fun getAvailableTeams(clubId: Int, season: Int): Single<List<Team>> {
        return Single.defer {
            defaultNetworkServiceCreator.getSwissFloorballService().getClubTeams(clubId, season)
                .subscribeOn(Schedulers.io())
                .map { clubTeamResponse ->
                    val mapper = TeamMapper()
                    mapper.mapTeams(clubTeamResponse)
                }
        }

    }
}