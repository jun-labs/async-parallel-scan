package project.alarm.app.core.user.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import project.alarm.app.core.user.application.UserSaveUseCase
import project.alarm.app.core.user.application.UserSearchUseCase
import project.alarm.app.core.user.domain.User
import project.alarm.app.core.user.persistence.UserDao
import java.util.stream.LongStream.rangeClosed
import kotlin.random.Random
import kotlin.streams.toList

@Service
class UserService(
    private val userDao: UserDao
) : UserSearchUseCase, UserSaveUseCase {

    @Transactional(readOnly = true)
    override fun findMaxUserId(): Long? {
        return userDao.findMaxUserId()
    }

    @Transactional(readOnly = true)
    override fun findByIdIn(
        startUserId: Long,
        endUserId: Long
    ): List<Long> {
        val userIdsRange = rangeClosed(startUserId, endUserId)
            .toList()
        return userDao.findByIdIn(userIdsRange)
    }

    override fun bulkInsert(
        startIndex: Long,
        endIndex: Long
    ) {
        val users = (startIndex..endIndex).map { idx ->
            User(_name = "User:$idx", _alarm = Random.nextBoolean())
        }
        insertUsers(users)
    }

    fun insertUsers(users: List<User>) {
        users.chunked(1_000).forEach { chunk ->
            userDao.bulkInsertUsers(chunk)
        }
    }
}
