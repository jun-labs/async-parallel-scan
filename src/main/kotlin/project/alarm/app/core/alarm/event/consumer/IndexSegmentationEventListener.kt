package project.alarm.app.core.alarm.event.consumer

import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import project.alarm.app.core.alarm.event.IndexDivisionEvent
import project.alarm.app.core.event.EventPublisher
import project.alarm.app.core.user.event.UserSearchEvent

@Component
class IndexSegmentationEventListener(
    private val eventPublisher: EventPublisher<UserSearchEvent>
) {
    @SqsListener(
        queueNames = ["\${application.amazon.sqs.event-divider.name}"],
        maxMessagesPerPoll = "10"
    )
    fun consume(@Payload event: IndexDivisionEvent) {
        val ranges = event.startIdx..event.endIdx
        ranges.chunked(1_000)
            .forEach { chunk ->
                val userSearchEvent = UserSearchEvent(chunk.first(), chunk.last())
                eventPublisher.publishEvent(userSearchEvent)
            }
    }
}
