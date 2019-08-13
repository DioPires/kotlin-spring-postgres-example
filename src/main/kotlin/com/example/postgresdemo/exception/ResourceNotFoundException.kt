package com.example.postgresdemo.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
//class ResourceNotFoundException(message: String, cause: Throwable? = null): RuntimeException(message = message, cause = cause)
class ResourceNotFoundException(message: String?): Exception(message)
