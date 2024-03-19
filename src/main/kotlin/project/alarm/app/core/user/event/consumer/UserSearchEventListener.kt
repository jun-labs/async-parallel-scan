package project.alarm.app.core.user.event.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import project.alarm.app.core.user.event.UserSearchEvent
import project.alarm.app.core.user.facade.UserFacade

@Component
class UserSearchEventListener(
    private val objectMapper: ObjectMapper,
    private val userFacade: UserFacade
) {

    @Async("asyncThreadPool")
    @SqsListener(
        queueNames = ["\${application.amazon.sqs.user-search.name}"],
        maxMessagesPerPoll = "10"
    )
    fun consume(@Payload message: String) {
        publishEvent(message)
    }

    @Async("asyncThreadPool")
    @SqsListener(
        queueNames = ["\${application.amazon.sqs.user-search.dead-letter-name}"],
        maxMessagesPerPoll = "10"
    )
    fun consumeDeadLetter(@Payload message: String) {
        publishEvent(message)
    }

    private fun publishEvent(message: String) {
        val snsMessage: Map<String, Any> = objectMapper.readValue(message)
        val strMessage = snsMessage["Message"]?.toString() ?: return
        if (strMessage.isBlank()) {
            return
        }

        val event: UserSearchEvent = objectMapper.readValue(strMessage)
        userFacade.findByIdIn(event.startIdx, event.endIdx)
    }
}
