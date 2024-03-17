package project.alarm.app.common.external

interface AlarmSender {
    fun send(userIds: List<Long>)
}
