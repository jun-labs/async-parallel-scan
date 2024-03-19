package project.alarm.app.core.alarm.event.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import project.alarm.app.core.alarm.event.IndexDivisionEvent
import project.alarm.app.core.event.EventPublisher
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class IndexDividerPublisher(
    @Value("\${application.amazon.sqs.event-divider.name}")
    private val queue: String,

    @Value("\${application.amazon.sqs.event-divider.topic}")
    private val topic: String,

    @Value("\${application.amazon.sqs.event-divider.dead-letter-name}")
    private val deadLetterQueue: String,

    @Value("\${application.amazon.sqs.event-divider.dead-letter-topic}")
    private val deadLetterTopic: String,

    private val snsClient: SnsClient,
    private val objectMapper: ObjectMapper
) : EventPublisher<IndexDivisionEvent> {

    override fun publishEvent(event: IndexDivisionEvent) {
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
