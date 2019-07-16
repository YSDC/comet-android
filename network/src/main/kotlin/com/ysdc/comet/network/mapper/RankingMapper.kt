package com.ysdc.comet.network.mapper

import com.ysdc.comet.model.RankEntry
import com.ysdc.comet.network.response.RankingResponse


class RankingMapper {
    fun mapRanking(rankingResponse: RankingResponse) : List<RankEntry>{
        val rankEntries : MutableList<RankEntry> = ArrayList()
        if(rankingResponse.data?.regions?.size == 1){
            rankingResponse.data?.regions!![0].rows?.forEach {rank ->
                if(rank.cells?.size == 10){
                    val entry = RankEntry.Builder()
                        .teamId(rank.rowData!!.team!!.id!!)
                        .rank(cellContentAtIndex(rank,0).toInt())
                        .picture(rank.cells!![1].image!!.url!!)
                        .teamName(cellContentAtIndex(rank,2))
                        .played(cellContentAtIndex(rank,3).toInt())
                        .win(cellContentAtIndex(rank,4).toInt())
                        .winOverTime(cellContentAtIndex(rank,5).toInt())
                        .looseOverTime(cellContentAtIndex(rank,6).toInt())
                        .loose(cellContentAtIndex(rank,7).toInt())
                        .goals(cellContentAtIndex(rank,8))
                        .goalsDiff(cellContentAtIndex(rank,9).toInt())


                }
            }
        }
        return rankEntries
    }

    private fun cellContentAtIndex(row: RankingResponse.Data.Regions.Row, index : Int) : String{
        return row.cells!![index].text!![0]
    }
}