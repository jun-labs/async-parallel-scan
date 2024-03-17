package project.alarm.app.common.utils

class IndexRangeDivider {
    companion object {
        fun calculateStartIdx(
            totalCount: Int,
            idx: Int,
            divisionCount: Int
        ): Long {
            val execution = totalCount / divisionCount
            val result = idx * execution
            return (result + 1).toLong()
        }

        fun calculateEndIdx(
            totalCount: Int,
            idx: Int,
            divisionCount: Int
        ): Long {
            if (idx < divisionCount - 1) {
                return (idx + 1) * (totalCount / divisionCount).toLong()
            }
            return totalCount.toLong()
        }
    }
}
