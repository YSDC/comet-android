package com.ysdc.comet.repositories

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.model.Team
import com.ysdc.comet.network.DefaultNetworkServiceCreator
import com.ysdc.comet.network.mapper.TeamMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TeamRepository(
    private val defaultNetworkServiceCreator: DefaultNetworkServiceCreator,
    private val generalConfig: GeneralConfig,
    private val dataManager: DataManager
    ) {
    fun getAvailableTeams(season: Int): Single<List<Team>> {
        return Single.defer {
            defaultNetworkServiceCreator.getSwissFloorballService().getClubTeams(generalConfig.clubId(), season)
                .subscribeOn(Schedulers.io())
                .map { clubTeamResponse ->
                    val mapper = TeamMapper()
                    mapper.mapTeams(clubTeamResponse)
                }
        }

    }

    fun teamExist(code : String) : Single<Boolean> {
        return dataManager.teamExist(code).subscribeOn(Schedulers.io())
    }
}