package project.alarm.app.core.alarm.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import project.alarm.app.common.external.TimeRecorder
import project.alarm.app.core.alarm.facade.AlarmFacade

@RestController
@RequestMapping("/api/events")
class AlarmSendingAPI(
    private val alarmFacade: AlarmFacade,
    private val timeRecorder: TimeRecorder
) {

    @PostMapping
    fun sendAlarm(): ResponseEntity<Any> {
        timeRecorder.recordStartTime()
        alarmFacade.sendAlarm()
        return ResponseEntity.ok()
            .build()
    }
}
