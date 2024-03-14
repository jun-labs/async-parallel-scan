package project.alarm.app.test.event

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import project.alarm.app.common.utils.IndexRangeDivider.Companion.calculateEndIdx
import project.alarm.app.common.utils.IndexRangeDivider.Companion.calculateStartIdx

@DisplayName("[UnitTest] IndexRangeDivider 단위 테스트")
class EventDividerUnitTest : BehaviorSpec({
    Given("전체 데이터 개수와 ChunkSize를 알고 있을 때") {
        val totalCount = 20_000_000
        val divisionCount = 500

        When("시작/종료 인덱스를 연산하면") {
            val startIdx = calculateStartIdx(totalCount, 2, divisionCount)
            val endIdx = calculateEndIdx(totalCount, 499, divisionCount)

            Then("올바른 값을 반환해야 한다.") {
                val expectedStartIdx = 80_001L
                val expectedEndIdx = 20_000_000L

                startIdx shouldBe expectedStartIdx
                endIdx shouldBe expectedEndIdx
            }
        }

        When("시작/종료 경계 값 인덱스를 연산하면") {
            val startIdx = calculateStartIdx(totalCount, 0, divisionCount)
            val endIdx = calculateEndIdx(totalCount, 500, divisionCount)

            Then("올바른 값을 반환해야 한다.") {
                val expectedStartIdx = 1L
                val expectedEndIdx = 20_000_000L

                startIdx shouldBe expectedStartIdx
                endIdx shouldBe expectedEndIdx
            }
        }
    }
})
