package project.alarm.app.core.user.facade

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import project.alarm.app.core.alarm.event.AlarmSendingEvent
import project.alarm.app.core.user.application.UserSearchUseCase

@Component
class UserFacade(
    private val userSearchUseCase: UserSearchUseCase,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun findByIdIn(startIdx: Long, endIdx: Long) {
        val ranges = startIdx..endIdx
        ranges.chunked(1_000)
            .forEach { chunk ->
                publishEvent(chunk)
            }
    }

    @Async(value = "asyncThreadPool")
    fun publishEvent(chunk: List<Long>) {
        val userIds = userSearchUseCase.findByIdIn(chunk.first(), chunk.last())
        val event = AlarmSendingEvent(userIds)
        eventPublisher.publishEvent(event)
    }
}
