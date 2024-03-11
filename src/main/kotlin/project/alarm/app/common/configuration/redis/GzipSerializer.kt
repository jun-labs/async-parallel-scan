package project.alarm.app.common.configuration.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class GzipSerializer<T>(
    private val objectMapper: ObjectMapper,
    private val type: Class<T>
) : RedisSerializer<T> {

    override fun serialize(data: T?): ByteArray? {
        if (data == null) return null

        return try {
            if (isPrimitiveType(data.javaClass)) {
                data.toString().toByteArray(Charsets.UTF_8)
            } else {
                ByteArrayOutputStream().use { byteStream ->
                    GZIPOutputStream(byteStream).use { gzipStream ->
                        objectMapper.writeValue(gzipStream, data)
                    }
                    byteStream.toByteArray()
                }
            }
        } catch (ex: Exception) {
            throw SerializationException("Could not serialize object: ${ex.message}", ex)
        }
    }

    override fun deserialize(bytes: ByteArray?): T? {
        if (bytes == null || bytes.isEmpty()) {
            return null
        }
        return try {
            if (isPrimitiveType(type)) {
                String(bytes, Charsets.UTF_8).convertType(type)
            } else {
                ByteArrayInputStream(bytes).use { byteStream ->
                    GZIPInputStream(byteStream).use { gzipStream ->
                        objectMapper.readValue(gzipStream, type)
                    }
                }
            }
        } catch (ex: Exception) {
            throw SerializationException("Could not deserialize data: ${ex.message}", ex)
        }
    }

    private fun isPrimitiveType(clazz: Class<*>): Boolean {
        return clazz.isPrimitive
                || clazz == String::class.java
                || Number::class.java.isAssignableFrom(clazz)
                || clazz == Boolean::class.java
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> String.convertType(type: Class<T>): T {
        return when (type) {
            String::class.java -> this as T
            Byte::class.java -> toByte() as T
            Short::class.java -> toShort() as T
            Int::class.java -> toInt() as T
            Long::class.java -> toLong() as T
            Float::class.java -> toFloat() as T
            Double::class.java -> toDouble() as T
            Boolean::class.java -> toBoolean() as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}
