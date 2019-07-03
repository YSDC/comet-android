package com.ysdc.comet.network.mapper

import com.ysdc.comet.model.Team
import com.ysdc.comet.network.response.ClubTeamsResponse

class TeamMapper {
    fun mapTeams(clubTeamsResponse: ClubTeamsResponse) : List<Team>{
        val teams : MutableList<Team> = ArrayList()
        clubTeamsResponse.entries?.forEach {entry ->
            teams.add(Team(entry.attributes.teamId, entry.teamName))
        }
        return teams
    }
}