package project.alarm.app.core.alarm.event

import com.fasterxml.jackson.annotation.JsonProperty

data class IndexDivisionEvent(
    @JsonProperty("startIdx")
    val startIdx: Long,

    @JsonProperty("endIdx")
    val endIdx: Long
)
