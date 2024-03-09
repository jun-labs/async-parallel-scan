package project.alarm.app.common.configuration.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class JsonGzipSerializer<T>(
    private val objectMapper: ObjectMapper,
    private val type: Class<T>
) : RedisSerializer<T> {

    override fun serialize(type: T?): ByteArray? {
        if (type == null) {
            return null
        }
        return try {
            ByteArrayOutputStream().use { byteStream ->
                GZIPOutputStream(byteStream).use { gzipStream ->
                    objectMapper.writeValue(gzipStream, type)
                }
                byteStream.toByteArray()
            }
        } catch (ex: Exception) {
            throw RuntimeException("Could not serialize object: ${ex.message}", ex)
        }
    }

    override fun deserialize(bytes: ByteArray?): T? {
        if (bytes == null || bytes.isEmpty()) {
            return null
        }
        return try {
            ByteArrayInputStream(bytes).use { byteStream ->
                GZIPInputStream(byteStream).use { gzipStream ->
                    objectMapper.readValue(gzipStream, type)
                }
            }
        } catch (ex: Exception) {
            throw RuntimeException("Could not deserialize bytes", ex)
        }
    }
}
