package com.ysdc.comet.network.service

import com.ysdc.comet.network.config.NetworkConstants.PARAM_API_KEY
import com.ysdc.comet.network.config.NetworkConstants.PARAM_CLUB_ID
import com.ysdc.comet.network.config.NetworkConstants.PARAM_GAME_CLASS
import com.ysdc.comet.network.config.NetworkConstants.PARAM_GROUP
import com.ysdc.comet.network.config.NetworkConstants.PARAM_LEAGUE
import com.ysdc.comet.network.config.NetworkConstants.PARAM_SEASON
import com.ysdc.comet.network.config.NetworkConstants.PARAM_SECRET
import com.ysdc.comet.network.config.NetworkConstants.PARAM_TEAM
import com.ysdc.comet.network.config.NetworkConstants.URL_AUTH
import com.ysdc.comet.network.config.NetworkConstants.URL_CLUB_TEAMS
import com.ysdc.comet.network.config.NetworkConstants.URL_RANKING
import com.ysdc.comet.network.config.NetworkConstants.URL_TEAM_DETAILS
import com.ysdc.comet.network.config.NetworkConstants.URL_TEAM_MATCHES
import com.ysdc.comet.network.response.*
import io.reactivex.Single
import retrofit2.http.*

interface SwissFloorballService {

    @FormUrlEncoded
    @POST(URL_AUTH)
    fun authenticate(@Field(PARAM_SECRET) secret: String, @Field(PARAM_API_KEY) apiKey: String): Single<AuthenticationResponse>

    /**
     * curl -v -X GET "https://api-v2.swissunihockey.ch/api/teams?mode=by_club&club_id=331&season=2019"
     */
    @GET(URL_CLUB_TEAMS)
    fun getClubTeams(@Query(PARAM_CLUB_ID) clubId: Int, @Query(PARAM_SEASON) season: Int): Single<ClubTeamsResponse>

    /**
     * curl -v -X GET "https://api-v2.swissunihockey.ch/api/games?mode=team&season=2019&team_id=415362&locale=fr-CH&games_per_page=100"
     */
    @GET(URL_TEAM_MATCHES)
    fun getTeamMatches(@Query(PARAM_TEAM) team: Int, @Query(PARAM_SEASON) season: Int): Single<TeamMatchesResponse>

    /**
     * Le classement
     * curl -v -X GET "https://api-v2.swissunihockey.ch/api/rankings?season=2018&league=6&game_class=12&group=Gruppe%201"
     */
    @GET(URL_RANKING)
    fun getRanking(@Query(PARAM_SEASON) season: Int, @Query(PARAM_LEAGUE) league: Int, @Query(PARAM_GAME_CLASS) gameClass: Int, @Query(PARAM_GROUP) group: String): Single<RankingResponse>

    /**
     * curl -v -X GET "https://api-v2.swissunihockey.ch/api/games?mode=list&season=2019&team_id=415362"
     */
    @GET(URL_TEAM_DETAILS)
    fun getTeamDetails(@Query(PARAM_SEASON) season: Int, @Query(PARAM_TEAM) team: Int): Single<TeamDetailsResponse>

}