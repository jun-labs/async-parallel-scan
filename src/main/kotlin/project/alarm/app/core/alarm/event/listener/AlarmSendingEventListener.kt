package project.alarm.app.core.alarm.event.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import project.alarm.app.common.external.AlarmSender
import project.alarm.app.core.alarm.event.AlarmSendingEvent

@Component
class AlarmSendingEventListener(
    private val alarmSender: AlarmSender
) {

    @EventListener
    fun consume(event: AlarmSendingEvent) {
        val userIds = event.userIds
        alarmSender.send(userIds)
    }
}
