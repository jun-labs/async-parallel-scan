package project.alarm.app.core.user.application

interface UserSearchUseCase {
    fun findMaxUserId(): Long?

    fun findByIdIn(startUserId: Long, endUserId: Long): List<Long>
}
