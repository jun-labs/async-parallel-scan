package project.alarm.app.core.user.application

interface UserSaveUseCase {
    fun bulkInsert(startIndex: Long, endIndex: Long)
}
