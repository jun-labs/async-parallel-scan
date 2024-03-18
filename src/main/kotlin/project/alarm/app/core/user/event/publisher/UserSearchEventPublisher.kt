package project.alarm.app.core.user.event.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import project.alarm.app.core.event.EventPublisher
import project.alarm.app.core.user.event.UserSearchEvent
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class UserSearchEventPublisher(
    @Value("\${application.amazon.sqs.user-search.name}")
    private val queueName: String,

    @Value("\${application.amazon.sqs.user-search.topic}")
    private val topic: String,

    private val snsClient: SnsClient,
    private val objectMapper: ObjectMapper
) : EventPublisher<UserSearchEvent> {

    override fun publishEvent(event: UserSearchEvent) {
        val eventAsString = objectMapper.writeValueAsString(event)
        val newEvent = PublishRequest.builder()
            .topicArn("$topic:$queueName")
            .message(eventAsString)
            .build()
        snsClient.publish(newEvent)
    }
}
