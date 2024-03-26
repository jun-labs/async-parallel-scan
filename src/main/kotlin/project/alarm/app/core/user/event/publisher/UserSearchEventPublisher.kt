package project.alarm.app.core.user.event.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import project.alarm.app.core.event.EventPublisher
import project.alarm.app.core.user.event.UserSearchEvent
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class UserSearchEventPublisher(
    @Value("\${application.amazon.sqs.user-search.name}")
    private val queue: String,

    @Value("\${application.amazon.sqs.user-search.topic}")
    private val topic: String,

    @Value("\${application.amazon.sqs.user-search.dead-letter-name}")
    private val deadLetterQueue: String,

    @Value("\${application.amazon.sqs.user-search.dead-letter-topic}")
    private val deadLetterTopic: String,

    private val snsClient: SnsClient,
    private val objectMapper: ObjectMapper
) : EventPublisher<UserSearchEvent> {

    override fun publishEvent(event: UserSearchEvent) {
        try {
            val newEvent = PublishRequest.builder()
                .topicArn("$topic:$queue")
                .message(objectMapper.writeValueAsString(event))
                .build()
            snsClient.publish(newEvent)
        } catch (ex: SdkException) {
            val deadLetter = PublishRequest.builder()
                .topicArn("$deadLetterTopic:$deadLetterQueue")
                .message(objectMapper.writeValueAsString(event))
                .build()
            snsClient.publish(deadLetter)
        }
    }
}
