package project.alarm.app.common.configuration.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

@Configuration
class RedisConfiguration {

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val objectMapper = ObjectMapper().apply {
            registerModule(JavaTimeModule())
        }
        val defaultSerializer = StringRedisSerializer()
        val gzipSerializer = object : RedisSerializer<Any> {
            override fun serialize(data: Any?): ByteArray? {
                return when (data) {
                    null -> null
                    is String, is Byte, is Short, is Int, is Long, is Float, is Double, is Char, is Boolean ->
                        defaultSerializer.serialize(data.toString())

                    else -> ByteArrayOutputStream().use { byteStream ->
                        GZIPOutputStream(byteStream).use { gzipStream ->
                            objectMapper.writeValue(gzipStream, data)
                        }
                        byteStream.toByteArray()
                    }
                }
            }

            override fun deserialize(bytes: ByteArray?): Any? {
                if (bytes == null || bytes.isEmpty()) {
                    return null
                }
                return ByteArrayInputStream(bytes).use { byteStream ->
                    GZIPInputStream(byteStream).use { gzipStream ->
                        objectMapper.readValue(gzipStream, Any::class.java)
                    }
                }
            }
        }

        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory
            keySerializer = StringRedisSerializer()
            hashKeySerializer = StringRedisSerializer()
            valueSerializer = gzipSerializer
            hashValueSerializer = gzipSerializer
        }
    }
}
