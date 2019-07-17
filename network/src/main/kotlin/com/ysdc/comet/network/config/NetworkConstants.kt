package com.ysdc.comet.network.config


object NetworkConstants {
    const val TIMEOUT_IN_SECONDS: Long = 20

    const val PARAM_CLUB_ID = "club_id"
    const val PARAM_API_KEY = "api_key"
    const val PARAM_SECRET = "secret"
    const val PARAM_TEAM = "team_id"
    const val PARAM_SEASON = "season"
    const val PARAM_LEAGUE = "league"
    const val PARAM_GAME_CLASS = "game_class"
    const val PARAM_GROUP = "group"

    const val URL_AUTH = "/bo/session/auth"
    const val URL_TEAM_MATCHES = "/api/games?mode=team&games_per_page=100&locale=fr-CH"
    const val URL_CLUB_TEAMS = "/api/teams?mode=by_club&locale=fr-CH"
    const val URL_RANKING = "/api/rankings?locale=fr-CH"
    const val URL_TEAM_DETAILS = "/api/games?mode=list&locale=fr-CH"
}