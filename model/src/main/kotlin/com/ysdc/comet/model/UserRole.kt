package com.ysdc.comet.model

import androidx.annotation.StringRes

enum class UserRole constructor(
    val id: String, @param:StringRes @field:StringRes
    val key: Int
) {
    UNDEFINED("", R.string.empty_string),
    PLAYER("player", R.string.role_player),
    GOALIE("goalie", R.string.role_goalie),
    TRAINER("trainer", R.string.role_trainer),
    OTHER("other", R.string.role_other);

    companion object {
        fun fromId(id: String): UserRole {
            return values().filter { l -> l.id == id }.getOrElse(0) { default() }
        }

        fun default(): UserRole {
            return UNDEFINED
        }
    }
}