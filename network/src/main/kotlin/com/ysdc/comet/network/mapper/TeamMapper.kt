package com.ysdc.comet.network.mapper

import com.ysdc.comet.model.Team
import com.ysdc.comet.network.response.ClubTeamsResponse
import com.ysdc.comet.network.response.StandardResponse

class TeamMapper {

    fun mapTeams(clubId: Int, clubTeamsResponse: ClubTeamsResponse): List<Team> {
        val teams: MutableList<Team> = ArrayList()
        clubTeamsResponse.entries?.forEach { entry ->
            teams.add(Team(clubId, entry.attributes.teamId, entry.teamName))
        }
        return teams
    }

    fun extractTeamDetails(teamDetailsResponse: StandardResponse, team: Team): Team? {
        return teamDetailsResponse.data?.context?.let {
            team.gameClass = it.gameClass
            team.group = it.group
            team.league = it.league
            team
        }
    }
}