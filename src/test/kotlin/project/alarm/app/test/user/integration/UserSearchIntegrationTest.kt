package project.alarm.app.test.user.integration

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import project.alarm.app.core.user.application.UserSaveUseCase
import project.alarm.app.core.user.application.UserSearchUseCase
import project.alarm.app.test.IntegrationTestBase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@DisplayName("[IntegrationTest] 사용자 조회 테스트")
class UserSearchIntegrationTest : IntegrationTestBase() {

    @Autowired
    private lateinit var userSaveUseCase: UserSaveUseCase

    @Autowired
    private lateinit var userSearchUseCase: UserSearchUseCase

    @Test
    @DisplayName("사용자를 저장하면 이를 조회할 수 있다.")
    fun searchMaxUserIdTest() {
        val startIdx = 1L
        val endIdx = 100L
        userSaveUseCase.bulkInsert(startIdx, endIdx)

        val maxUserId = userSearchUseCase.findMaxUserId()

        assertEquals(100L, maxUserId)
    }

    @Test
    @DisplayName("사용자가 존재하지 않으면 Null을 반환한다.")
    fun searchMaxIdNullTest() {
        assertNull(userSearchUseCase.findMaxUserId())
    }

    @Test
    @DisplayName("사용자를 저장하면 이를 조회할 수 있다.")
    fun searchUserIdFromStartIdxToEndIdx() {
        val startIdx = 1L
        val endIdx = 100L
        userSaveUseCase.bulkInsert(startIdx, endIdx)
        val findUserIds = userSearchUseCase.findByIdIn(startIdx, endIdx)

        assertTrue { findUserIds.isNotEmpty() }
    }
}
