package project.alarm.app.test.unittest

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.DisplayName
import org.junit.jupiter.api.Test
import project.alarm.app.common.configuration.redis.GzipSerializer
import project.alarm.app.logger

@DisplayName("[UnitTest] 레디스 데이터 압축 단위 테스트")
class GzipSerializerTest {

    private val log = logger()

    @Test
    fun redisGzipSizeTest() {
        val objectMapper = ObjectMapper()

        val serializer = GzipSerializer(objectMapper, List::class.java)
        val numbers = (1L..1000L).toList()

        val serializedObj = serializer.serialize(listOf(numbers as Any))
        val uncompressedObj = objectMapper.writeValueAsBytes(numbers)
        val compressionRatio = serializedObj!!.size.toDouble() / uncompressedObj.size.toDouble()

        log.info("Compressed:{}", serializedObj.size)
        log.info("UnCompressed:{}", uncompressedObj.size)
        log.info("Compressed-Ratio:{}", compressionRatio)
    }
}
