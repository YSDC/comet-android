package com.ysdc.comet.network.mapper

import com.ysdc.comet.model.RankEntry
import com.ysdc.comet.network.response.RankingResponse


class RankingMapper {
    fun mapRanking(rankingResponse: RankingResponse) : List<RankEntry>{
        val rankEntries : MutableList<RankEntry> = ArrayList()
        rankingResponse.entries?.forEach {entry ->
            teams.add(Team(entry.attributes.teamId, entry.teamName))
        }
        return rankEntries
    }
}