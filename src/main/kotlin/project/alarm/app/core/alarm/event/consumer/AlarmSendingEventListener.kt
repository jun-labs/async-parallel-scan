package project.alarm.app.core.alarm.event.consumer

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import project.alarm.app.common.external.AlarmSender
import project.alarm.app.core.alarm.event.AlarmSendingEvent

@Component
class AlarmSendingEventListener(
    private val alarmSender: AlarmSender
) {

    @Async("asyncThreadPool")
    @EventListener
    fun consumeEvent(event: AlarmSendingEvent) {
        val userIds = event.userIds
        alarmSender.send(userIds)
    }
}
