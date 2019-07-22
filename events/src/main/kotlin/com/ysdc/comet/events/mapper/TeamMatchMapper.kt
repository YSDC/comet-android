package com.ysdc.comet.events.mapper

import com.ysdc.comet.common.utils.AppConstants.EMPTY_STRING
import com.ysdc.comet.events.model.MatchEvent
import com.ysdc.comet.network.response.StandardResponse

class TeamMatchMapper {
    fun parseTeamMatchesIds(response: StandardResponse): List<Int> {
        val ids = ArrayList<Int>()
        response.data?.regions?.first()?.rows?.forEach { row ->
            row.teamId?.ids?.first()?.let {
                ids.add(it)
            }
        }
        return ids
    }

    fun parseTeamMatch(response: StandardResponse): MatchEvent {
        val matchEvent = MatchEvent.Builder()
        response.data?.regions?.first()?.rows?.first()?.cells?.forEachIndexed { index, cell ->
            when (index) {
                0 -> matchEvent.homeLogo = cell.image?.url
                1 -> matchEvent.homeTeam = cell.text?.first() ?: EMPTY_STRING
                2 -> matchEvent.awayLogo = cell.image?.url
                3 -> matchEvent.awayTeam = cell.text?.first() ?: EMPTY_STRING
                4 -> matchEvent.score = cell.text?.first() ?: EMPTY_STRING
                5 -> matchEvent.date = cell.text?.first() ?: EMPTY_STRING
                6 -> matchEvent.time = cell.text?.first() ?: EMPTY_STRING
                7

            }
        }
        matchEvent.id
    }
}