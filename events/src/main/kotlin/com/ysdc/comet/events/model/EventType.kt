package com.ysdc.comet.events.model

import androidx.annotation.StringRes
import com.ysdc.comet.events.R

enum class EventType(
    @param:StringRes @field:StringRes
    val key: Int
) {

    TOURNAMENT_MATCH(R.string.event_type_tournament),
    CUP_MATCH(R.string.event_type_cup),
    TRAINING(R.string.event_type_training),
    FRIENDLY_MATCH(R.string.event_type_friendly_match),
    OTHER(R.string.event_type_other);


    companion object {

        fun default(): EventType {
            return OTHER
        }
    }
}