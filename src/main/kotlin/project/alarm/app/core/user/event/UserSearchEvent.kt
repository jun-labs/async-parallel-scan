package project.alarm.app.core.user.event

import com.fasterxml.jackson.annotation.JsonProperty

data class UserSearchEvent(
    @JsonProperty("startIdx")
    val startIdx: Long,

    @JsonProperty("endIdx")
    val endIdx: Long
)
