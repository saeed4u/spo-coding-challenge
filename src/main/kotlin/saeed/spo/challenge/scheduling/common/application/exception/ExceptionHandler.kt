package saeed.spo.challenge.scheduling.common.application.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import saeed.spo.challenge.scheduling.common.model.ApiResponse

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ValidationException::class, HttpMediaTypeNotSupportedException::class, Exception::class)
    fun handleException(ex: Exception, request: WebRequest): ResponseEntity<ApiResponse> {
        return when (ex) {
            is ValidationException -> {
                handleException(ex.message!!, HttpStatus.UNPROCESSABLE_ENTITY)
            }
            is HttpMediaTypeNotSupportedException -> {
                handleException(ex.message!!, HttpStatus.UNSUPPORTED_MEDIA_TYPE)
            }
            else -> handleException()
        }
    }

    private fun handleException(errors: String = "An error occurred while processing your request",
                                status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR): ResponseEntity<ApiResponse> {
        return ResponseEntity(ApiResponse(true, errors, null), status)
    }


}