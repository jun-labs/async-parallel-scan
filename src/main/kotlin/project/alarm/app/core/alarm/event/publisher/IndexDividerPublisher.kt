package project.alarm.app.core.alarm.event.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import project.alarm.app.core.alarm.event.IndexDivisionEvent
import project.alarm.app.core.event.EventPublisher
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class IndexDividerPublisher(
    @Value("\${application.amazon.sqs.event-divider.name}")
    private val queueName: String,

    @Value("\${application.amazon.sqs.event-divider.topic}")
    private val topic: String,

    private val snsClient: SnsClient,
    private val objectMapper: ObjectMapper
) : EventPublisher<IndexDivisionEvent> {

    @Async("asyncThreadPool")
    override fun publishEvent(event: IndexDivisionEvent) {
        val newEvent = PublishRequest.builder()
            .topicArn("$topic:$queueName")
            .message(objectMapper.writeValueAsString(event))
            .build()
        snsClient.publish(newEvent)
    }
}
