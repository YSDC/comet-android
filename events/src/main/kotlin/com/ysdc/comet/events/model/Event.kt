package com.ysdc.comet.events.model

import java.util.*

interface Event {
    val id: String
    val name: String
    val date: Date
    val state: EventState
    val type: EventType
    val location: EventLocation
    val imageUrl: String
}