package project.alarm.app.core.user.event.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import project.alarm.app.core.user.event.UserSearchEvent
import project.alarm.app.core.user.facade.UserFacade

@Component
class UserSearchEventListener(
    private val userFacade: UserFacade
) {

    @EventListener
    fun consume(event: UserSearchEvent) {
        userFacade.findByIdIn(event.startIdx, event.endIdx)
    }
}
