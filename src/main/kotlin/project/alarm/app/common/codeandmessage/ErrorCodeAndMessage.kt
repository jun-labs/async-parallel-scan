package project.alarm.app.common.codeandmessage

import org.springframework.http.HttpStatus

enum class ErrorCodeAndMessage(
    private val status: HttpStatus,
    private val message: String
) : CodeAndMessage {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    override fun getCode(): Int {
        return status.value()
    }

    override fun getMessage(): String {
        return message
    }
}
