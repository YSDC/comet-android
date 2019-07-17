package com.ysdc.comet.network.mapper

import com.github.ajalt.timberkt.Timber
import com.ysdc.comet.model.RankEntry
import com.ysdc.comet.model.Ranking
import com.ysdc.comet.network.response.RankingResponse


class RankingMapper {
    fun mapRanking(rankingResponse: RankingResponse): Ranking {
        val rankEntries: MutableList<RankEntry> = ArrayList()
        var isLargeField = false
        if (rankingResponse.data?.regions?.size == 1) {
            rankingResponse.data?.regions!![0].rows?.forEach { rank ->
                when (rank.cells?.size) {
                    10 -> {
                        isLargeField = false
                        val entry = RankEntry.Builder()
                            .teamId(rank.rowData!!.team!!.id!!)
                            .rank(cellContentAtIndex(rank, 0).toInt())
                            .picture(rank.cells!![1].image!!.url!!)
                            .teamName(cellContentAtIndex(rank, 2))
                            .played(cellContentAtIndex(rank, 3).toInt())
                            .win(cellContentAtIndex(rank, 4).toInt())
                            .draw(cellContentAtIndex(rank, 5).toInt())
                            .loose(cellContentAtIndex(rank, 6).toInt())
                            .goals(cellContentAtIndex(rank, 7))
                            .goalsDiff(cellContentAtIndex(rank, 8))
                            .points(cellContentAtIndex(rank, 9).toInt())
                        rankEntries.add(entry.build())
                    }
                    11 -> {
                        isLargeField = true
                        val entry = RankEntry.Builder()
                            .teamId(rank.rowData!!.team!!.id!!)
                            .rank(cellContentAtIndex(rank, 0).toInt())
                            .picture(rank.cells!![1].image!!.url!!)
                            .teamName(cellContentAtIndex(rank, 2))
                            .played(cellContentAtIndex(rank, 3).toInt())
                            .win(cellContentAtIndex(rank, 4).toInt())
                            .winOverTime(cellContentAtIndex(rank, 5).toInt())
                            .looseOverTime(cellContentAtIndex(rank, 6).toInt())
                            .loose(cellContentAtIndex(rank, 7).toInt())
                            .goals(cellContentAtIndex(rank, 8))
                            .goalsDiff(cellContentAtIndex(rank, 9))
                            .points(cellContentAtIndex(rank, 10).toInt())
                        rankEntries.add(entry.build())
                    }
                    else -> {
                        Timber.e { "Whoops, unexpected" }
                    }
                }
            }
        }
        return Ranking(isLargeField, rankEntries)
    }

    private fun cellContentAtIndex(row: RankingResponse.Data.Regions.Row, index: Int): String {
        return row.cells!![index].text!![0]
    }
}