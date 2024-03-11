package project.alarm.app.common.codeandmessage

import org.springframework.http.HttpStatus

enum class SuccessCodeAndMessage(
    private val status: HttpStatus,
    private val message: String
) : CodeAndMessage {
    OK(HttpStatus.OK, "OK");

    override fun getCode(): Int {
        return status.value()
    }

    override fun getMessage(): String {
        return message
    }
}
