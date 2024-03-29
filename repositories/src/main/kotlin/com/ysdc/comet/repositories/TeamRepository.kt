package com.ysdc.comet.repositories

import com.ysdc.comet.common.application.GeneralConfig
import com.ysdc.comet.common.data.prefs.MyPreferences
import com.ysdc.comet.common.data.prefs.PrefsConstants.PREFS_TEAM
import com.ysdc.comet.common.exception.ValidationException
import com.ysdc.comet.data.DataManager
import com.ysdc.comet.model.Ranking
import com.ysdc.comet.model.Team
import com.ysdc.comet.network.DefaultNetworkServiceCreator
import com.ysdc.comet.network.mapper.TeamMapper
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TeamRepository(
    private val defaultNetworkServiceCreator: DefaultNetworkServiceCreator,
    private val generalConfig: GeneralConfig,
    private val dataManager: DataManager,
    private val preferences: MyPreferences
    ) {

    fun getTeam() : Team?{
        val teamJson = preferences.getAsString(PREFS_TEAM)
        return teamJson?.let { generalConfig.getMoshi().adapter(Team::class.java).fromJson(it) }
    }

    fun getAvailableTeams(clubId : Int, season: Int): Single<List<Team>> {
        return Single.defer {
            defaultNetworkServiceCreator.getSwissFloorballService().getClubTeams(clubId, season)
                .subscribeOn(Schedulers.io())
                .map { clubTeamResponse ->
                    val mapper = TeamMapper()
                    mapper.mapTeams(clubId, clubTeamResponse)
                }
        }

    }

    fun validateActivationCode(code : String) : Single<Boolean> {
        return dataManager.isClubTokenValid(code).subscribeOn(Schedulers.io())
    }

    fun registerTeam(season: Int) : Completable {
        return Completable.defer {
            val team = getTeam()
            if(team == null){
                //TODO: Add error message
                Completable.error(ValidationException(""))
            }else{
                defaultNetworkServiceCreator.getSwissFloorballService().getTeamDetails(season, team.teamId)
                    .subscribeOn(Schedulers.io())
                    .map { response ->
                        TeamMapper().extractTeamDetails(response, team)
                        team
                    }
                    .flatMapCompletable{newTeam -> dataManager.registerTeam(newTeam)}
            }

        }
    }

    fun updateTeamLocally(teamSelected: Team) {
        val teamJson = generalConfig.getMoshi().adapter(Team::class.java).toJson(teamSelected)
        preferences.setString(PREFS_TEAM, teamJson)
    }

//    fun getTeamRanking(season: Int) : Single<Ranking>{
//        return Single.defer {
//            defaultNetworkServiceCreator.getSwissFloorballService().getRanking()
//        }
//    }
}