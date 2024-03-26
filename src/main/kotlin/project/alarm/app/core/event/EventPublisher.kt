package project.alarm.app.core.event

interface EventPublisher<T> {
    fun publishEvent(event: T)
}
