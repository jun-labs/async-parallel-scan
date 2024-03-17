package project.alarm.app.common.external.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import project.alarm.app.common.external.AlarmSender
import project.alarm.app.common.external.TimeRecorder

@Service
class RedisService(
    private val redisTemplate: RedisTemplate<String, Any>
) : AlarmSender, TimeRecorder {

    override fun send(userIds: List<Long>) {
        redisTemplate.opsForValue()
            .increment(getKey("accepted"), userIds.size.toLong())
        recordEndTime()
    }

    override fun recordStartTime() {
        redisTemplate.opsForValue()
            .set(getTimeKey("startTime"), System.currentTimeMillis())
    }

    override fun recordEndTime() {
        redisTemplate.opsForValue()
            .set(getTimeKey("endTime"), System.currentTimeMillis())
    }

    private fun getKey(key: String): String {
        return "alarm::string::${key}"
    }

    private fun getTimeKey(key: String): String {
        return "time::string::${key}"
    }
}
