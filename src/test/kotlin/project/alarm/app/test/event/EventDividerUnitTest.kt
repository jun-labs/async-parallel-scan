package project.alarm.app.test.event


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import project.alarm.app.common.utils.IndexRangeDivider.Companion.calculateEndIdx
import project.alarm.app.common.utils.IndexRangeDivider.Companion.calculateStartIdx

@DisplayName("[UnitTest] IndexRangeDivider 단위 테스트")
class EventDividerUnitTest {

    @Test
    @DisplayName("전체 데이터 개수와 ChunkSize를 알고 있을 때 시작/종료 인덱스를 알 수 있다.")
    fun testCalculateIndex() {
        val totalCount = 20_000_000
        val divisionCount = 500

        // 시작/종료 인덱스 연산
        val startIdxCase1 = calculateStartIdx(totalCount, 2, divisionCount)
        val endIdxCase1 = calculateEndIdx(totalCount, 499, divisionCount)

        // 경계 값 인덱스 연산
        val startIdxCase2 = calculateStartIdx(totalCount, 0, divisionCount)
        val endIdxCase2 = calculateEndIdx(totalCount, 500, divisionCount)

        // 연산 결과 검증
        assertEquals(80_001L, startIdxCase1, "시작 인덱스 계산이 올바르지 않습니다.")
        assertEquals(20_000_000L, endIdxCase1, "종료 인덱스 계산이 올바르지 않습니다.")
        assertEquals(1L, startIdxCase2, "경계값에서의 시작 인덱스 계산이 올바르지 않습니다.")
        assertEquals(20_000_000L, endIdxCase2, "경계값에서의 종료 인덱스 계산이 올바르지 않습니다.")
    }
}
