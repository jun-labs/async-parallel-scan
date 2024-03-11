package project.alarm.app.common.exception

import project.alarm.app.common.codeandmessage.ErrorCodeAndMessage.INTERNAL_SERVER_ERROR

class DaoException(
    val errorMessage: String?
) : RuntimeException(INTERNAL_SERVER_ERROR.getMessage()) {
    val codeAndMessage = INTERNAL_SERVER_ERROR
}
