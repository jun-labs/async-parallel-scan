package project.alarm.app.core.alarm.facade

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import project.alarm.app.common.utils.IndexRangeDivider.Companion.calculateEndIdx
import project.alarm.app.common.utils.IndexRangeDivider.Companion.calculateStartIdx
import project.alarm.app.core.alarm.event.IndexDivisionEvent
import project.alarm.app.core.user.application.UserSearchUseCase

@Component
class AlarmFacade(
    private val userSearchUseCase: UserSearchUseCase,
    private val eventPublisher: ApplicationEventPublisher
) {

    @Async(value = "asyncThreadPool")
    fun sendAlarm() {
        val maxUserId = userSearchUseCase.findMaxUserId() ?: return
        for (idx in 0 until DIVISION_COUNT) {
            val startIdx = calculateStartIdx(maxUserId.toInt(), idx, DIVISION_COUNT)
            val endIdx = calculateEndIdx(maxUserId.toInt(), idx, DIVISION_COUNT)
            val event = IndexDivisionEvent(startIdx, endIdx)
            eventPublisher.publishEvent(event)
        }
    }

    companion object {
        private const val DIVISION_COUNT = 500
    }
}
