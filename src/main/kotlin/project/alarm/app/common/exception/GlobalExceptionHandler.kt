package project.alarm.app.common.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import project.alarm.app.common.codeandmessage.ErrorCodeAndMessage
import project.alarm.app.logger

@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = logger()

    @ExceptionHandler(DaoException::class)
    fun resolveDaoException(ex: DaoException): ResponseEntity<ErrorResponse> {
        log.error("error:{}", ex.errorMessage)
        val codeAndMessage = ex.codeAndMessage
        return ResponseEntity.status(codeAndMessage.getCode())
            .body(ErrorResponse(codeAndMessage))
    }
}

data class ErrorResponse(
    private val codeAndMessage: ErrorCodeAndMessage
) {
    val code: Int = codeAndMessage.getCode()
    val message: String = codeAndMessage.getMessage()
}
